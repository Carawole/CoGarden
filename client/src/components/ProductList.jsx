
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Container, Row, Col, Card, Button, Spinner, Modal } from 'react-bootstrap';
import { addToCart } from './CartContext';  // Import the cart context
import '../styles/ProductList.css';

const ProductList = ({ categories, loggedInUser, cart, setCartVersion }) => {
    const { title } = useParams();
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState(null);

    const fetchProducts = () => {
        fetch(`http://localhost:8080/api/product?category=${title}`)
            .then((response) => response.json())
            .then((data) => {
                setProducts(data.payload);
                setLoading(false);
                console.log(loggedInUser);
            })
            .catch((error) => {
                console.error('Error:', error);
                setLoading(false);
            });
    }

    useEffect(() => {
        fetchProducts();
    }, [title])

    useEffect(() => {
        console.log('Updated Products:', products);
    }, [products]);

    // useEffect(() => {
    //     const fetchProducts = async () => {
    //         try {
    //             const response = await fetch(`http://localhost:8080/api/product?category=${title}`);
    //             if (!response.ok) throw new Error('Failed to fetch products');
    //             const data = await response.json();
    //             setProducts(data.Body);
    //             console.log(title);
    //             console.log(data);
    //             console.log(products);
    //             setLoading(false);
    //         } catch (error) {
    //             console.error('Error:', error);
    //             setLoading(false);
    //         }
    //     };

    //     fetchProducts();
    // }, [title]);

    const handleShowModal = (product) => {
        setSelectedProduct(product);
        setShowModal(true);
    };

    const handleCloseModal = () => setShowModal(false);

    const handleAddToCart = () => {
        addToCart(loggedInUser, cart, selectedProduct);
        setCartVersion((prev) => prev + 1);
        handleCloseModal();
    };

    if (loading) {
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" />
            </Container>
        );
    }

    return (
        <Container className="my-5">
            <h2 className="text-center mb-4">Products in {title}</h2>
            <Row>
                {loading ? (
                    <Spinner animation="border" />
                ) : products && products.length > 0 ? (
                    products.map((product) => (
                        <Col key={product.productId} sm={12} md={6} lg={4} className="mb-4">
                            <Card className="product-card" onClick={() => handleShowModal(product)}>
                                <Card.Img variant="top" src={product.image_url || '/placeholder.jpg'} alt={product.productName} />
                                <Card.Body>
                                    <Card.Title>{product.productName}</Card.Title>
                                    <Card.Text>
                                        {product.description}
                                    </Card.Text>
                                    <Card.Text>
                                        <strong>${product.price.toFixed(2)}</strong>
                                    </Card.Text>
                                    <>
                                        {loggedInUser ? (
                                            loggedInUser.isAdmin ? (
                                                <>
                                                    <Button variant="primary" onClick={(e) => { e.stopPropagation(); handleShowModal(product); }}>
                                                        View Details
                                                    </Button>
                                                    <Button variant="danger" onClick={(e) => { e.stopPropagation(); handleShowModal(product); }}>
                                                        Delete
                                                    </Button>
                                                </>
                                            ) : (
                                                <Button variant="primary" onClick={(e) => { e.stopPropagation(); handleShowModal(product); }}>
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
                    ))
                ) : (
                    <p>No products found for this category.</p>
                )}
            </Row>

            {/* Product Modal */}
            {selectedProduct && (
                <Modal show={showModal} onHide={handleCloseModal} centered>
                    <Modal.Header closeButton>
                        <Modal.Title>{selectedProduct.productName}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <img src={selectedProduct.image_url || '/placeholder.jpg'} alt={selectedProduct.productName} className="img-fluid mb-3" />
                        <p>{selectedProduct.description}</p>
                        <h4>${selectedProduct.price.toFixed(2)}</h4>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseModal}>
                            Close
                        </Button>
                        <Button variant="success" onClick={handleAddToCart}>
                            Add to Cart
                        </Button>
                    </Modal.Footer>
                </Modal>
            )}
        </Container>
    );
};

export default ProductList;