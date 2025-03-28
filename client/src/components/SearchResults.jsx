import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Container, Row, Col, Card, Spinner, Alert, Button, Modal } from 'react-bootstrap';
import { addToCart } from './CartContext';  // Import the cart context

const SearchResults = ({ loggedInUser, cart, setLoading, setCartVersion }) => {
    const navigate = useNavigate();

    const location = useLocation();
    const params = new URLSearchParams(location.search);
    
    const query = params.get('query');
    const category = params.get('category');
    const source = params.get('source');

    const [results, setResults] = useState([]);
    const [error, setError] = useState(null);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchResults = async () => {
            try {
                let url;
                if (source === 'internal') {
                    if (category === 'all') {
                        url = `http://localhost:8080/api/product?productName=${query}&category=`;
                    } else {
                        url = `http://localhost:8080/api/product?productName=${query}&category=${category}`;
                    }
                } else {
                    url = `http://localhost:8080/api/perenual/search?query=${query}`;
                }

                let providedHeaders;
                if (loggedInUser) {
                    providedHeaders = {
                        'Content-Type': 'application/json',
                        'Authorization': loggedInUser.jwt
                    };
                } else {
                    providedHeaders = {
                        'Content-Type': 'application/json'
                    };
                }
                const response = await fetch(url, {
                    method: 'GET',
                    headers: providedHeaders,
                });
                if (!response.ok) throw new Error('Failed to fetch results');

                const data = await response.json();
                setResults(data.data || data.payload);  // Adjust based on API structure
            } catch (error) {
                setError(error.message);
            }  finally {
                setTimeout(() => {
                    setIsLoading(false);
                  }, 2000);
            }
        };
        fetchResults();
    }, [query, category, source]);


    const handleShowModal = (item) => {
        setSelectedProduct(item);
        setShowModal(true);
    };

    const handleCloseModal = () => setShowModal(false);

    const handleAddToCart = () => {
        setLoading(true);
        addToCart(loggedInUser, cart, selectedProduct);
        setCartVersion((prev) => prev + 1);
        handleCloseModal();
    };

    if (isLoading) {
        return (
            <div className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
              <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
              </Spinner>
            </div>
          );
    }

    if (error) return <Alert variant="danger">Error: {error}</Alert>;

    return (
        <Container className="mt-5">
            <h3>Results for "{query}" </h3>
            <Row>
                {results.length > 0 ? results.map((item) => (
                    <Col key={item.id || item.productId} md={4} className="mb-4">
                        <Card>
                            <Card.Img variant="top" src={item.default_image?.original || item.image} />
                            <Card.Body>
                                <Card.Title>{item.common_name || item.productName}</Card.Title>
                                <Card.Text>
                                    {item.scientific_name ? `Scientific Name: ${item.scientific_name.join(', ')}` : `Price: $${item.price.toFixed(2)}`}
                                </Card.Text>
                                <Card.Text>
                                    {item.category ? `Category: ${item.category}` : ''}
                                </Card.Text>
                                <Card.Text>
                                    {item.description ? item.description : ''}
                                </Card.Text>
                                <>
                                    {loggedInUser ? (
                                        loggedInUser.isAdmin ? (
                                            <>
                                                <Button variant="primary" onClick={(e) => { e.stopPropagation(); handleShowModal(item); }}>
                                                    View Details
                                                </Button>
                                            </>
                                        ) : (
                                            <Button variant="primary" onClick={(e) => { e.stopPropagation(); handleShowModal(item); }}>
                                                View Details
                                            </Button>
                                        )
                                    ) : (
                                        <Button variant="primary" disabled>
                                            Log in to view details
                                        </Button>
                                    )}
                                </>
                            </Card.Body>
                        </Card>
                    </Col>
                )) : <p>No results found.</p>}
            </Row>

            {/* Product Modal */}
            {selectedProduct && (
                <Modal show={showModal} onHide={handleCloseModal} centered>
                    <Modal.Header closeButton>
                        <Modal.Title>{selectedProduct.productName || selectedProduct.common_name || 'No Name Available'}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {/* <img src={selectedProduct.image_url || selectedProduct.default_image?.original || '/placeholder.jpg'} alt={selectedProduct.productName || selectedProduct.common_name || 'Product'} className="img-fluid mb-3" /> */}
                        <p>{selectedProduct.description || ''}</p>
                        {selectedProduct.price ? (
                            <h4>${selectedProduct.price.toFixed(2)}</h4>
                        ) : selectedProduct.scientific_name ? (
                            <h4>Scientific Name: {selectedProduct.scientific_name.join(', ')}</h4>
                        ) : null}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseModal}>
                            Close
                        </Button>
                        {selectedProduct.price ? (
                            <Button variant="success" onClick={handleAddToCart}>
                                Add to Cart
                            </Button>
                        ) : (
                            <Button variant="primary" onClick={(e) => { 
                                e.stopPropagation(); 
                                navigate('/add-product', { state: { productId: selectedProduct.id } }); 
                            }}>
                                Add to Products
                            </Button>
                        )}
                    </Modal.Footer>
                </Modal>
            )}
        </Container>
    );
};

export default SearchResults;
