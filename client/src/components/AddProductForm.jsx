import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Form, Button, Container, Row, Col, Spinner, Alert } from 'react-bootstrap';

export function AddProductForm({ loggedInUser, categories }) {
    const location = useLocation();
    const navigate = useNavigate();

    const productId = location.state?.productId;

    const [formData, setFormData] = useState({
        productName: '',
        category: '',
        description: '',
        cycle: '',
        watering: '',
        sunlight: '',
        hardinessZone: '',
        price: ''             
    });

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProductData = async () => {
            if (!productId) {
                setError("No product ID provided.");
                setLoading(false);
                return;
            }

            try {
                const response = await fetch(`http://localhost:8080/api/perenual/details?id=${productId}`,{
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': loggedInUser.jwt
                    }
                });
                if (!response.ok) {
                    throw new Error(`Failed to fetch product (status: ${response.status})`);
                }

                const product = await response.json();

                // Prefill form with fetched product data
                setFormData({
                    productName: product.common_name || '',
                    category: product.category || '',
                    description: product.description || '',
                    cycle: product.cycle || '',
                    watering: product.watering || '',
                    sunlight: product.sunlight.join(', ') || '',
                    hardinessZone: product.hardiness.min || '',
                    price:product.price || ''
                });

                setLoading(false);
            } catch (err) {
                console.error("Error fetching product:", err);
                setError("Failed to load product data.");
                setLoading(false);
            }
        };

        fetchProductData();
    }, [productId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        try {
            const response = await fetch('http://localhost:8080/api/product', {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json',
                    'Authorization': loggedInUser.jwt
                },
                body: JSON.stringify(formData)
            });

            if (response.status === 201) {
                console.log('Product added successfully!');
                navigate('/');  // Redirect after success
            } else {
                console.error('Failed to add product');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    if (loading) {
        return <Spinner animation="border" />;
    }

    if (error) {
        return <Alert variant="danger">{error}</Alert>;
    }

    return (
        <Container className="mt-5">
            <h2>Add New Product</h2>
            <Form onSubmit={handleSubmit}>
                <Row>
                    <Col md={6}>
                        <Form.Group className="mb-3">
                            <Form.Label>Product Name</Form.Label>
                            <Form.Control
                                type="text"
                                name="productName"
                                value={formData.productName}
                                onChange={handleChange}
                                placeholder="Enter product name"
                                required
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Category</Form.Label>
                            <Form.Select
                                name="category"
                                value={formData.category}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select category</option>
                                {categories.map((category) => (
                                    <option key={category.id || category.title}>
                                        {category.title}
                                    </option>
                                ))}
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Description</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                name="description"
                                value={formData.description}
                                onChange={handleChange}
                                placeholder="Enter description"
                            />
                        </Form.Group>
                    </Col>

                    <Col md={6}>
                        <Form.Group className="mb-3">
                            <Form.Label>Cycle</Form.Label>
                            <Form.Control
                                type="text"
                                name="cycle"
                                value={formData.cycle}
                                onChange={handleChange}
                                placeholder="Enter cycle"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Watering</Form.Label>
                            <Form.Control
                                type="text"
                                name="watering"
                                value={formData.watering}
                                onChange={handleChange}
                                placeholder="Enter watering needs"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Sunlight</Form.Label>
                            <Form.Control
                                type="text"
                                name="sunlight"
                                value={formData.sunlight}
                                onChange={handleChange}
                                placeholder="Enter sunlight needs"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Hardiness Zone</Form.Label>
                            <Form.Control
                                type="number"
                                name="hardinessZone"
                                value={formData.hardinessZone}
                                onChange={handleChange}
                                placeholder="Enter hardiness zone"
                                min="0"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Price</Form.Label>
                            <Form.Control
                                type="number"
                                name="price"
                                value={formData.price}
                                onChange={handleChange}
                                placeholder="Enter price"
                                step="0.01"
                                min="0"
                                required
                            />
                        </Form.Group>
                    </Col>
                </Row>

                <Button variant="primary" type="submit">Add Product</Button>
            </Form>
        </Container>
    );
};

export default AddProductForm;