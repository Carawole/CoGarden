import { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button, Spinner, Modal } from 'react-bootstrap';
import { submitOrder, removeFromCart, updateCart } from './CartContext';

export default function CartPage({ cart, loggedInUser, loading, setLoading, setCartVersion }) {

    const [quantities, setQuantities] = useState({});

    const handleQuantityChange = (cartItemId, value) => {
        setQuantities((prev) => ({
            ...prev,
            [cartItemId]: value
        }));
    };

useEffect(() => {
    setCartVersion((prev) => prev + 1);
}, []);

    const handleUpdate = (item) => {
        setLoading(true);
        updateCart(item, loggedInUser, quantities[item.cartItemId]);
        setCartVersion((prev) => prev + 1);
    }
  
    const handleSubmit = () => {
        setLoading(true);
        submitOrder(cart, loggedInUser);
        setCartVersion((prev) => prev + 1);
    }

    if (!cart || cart.cartItems.length === 0) return <div>No items in the cart.</div>;

    return (
        <Container className="mt-5">
            <h1>Cart</h1>
            <Row>
                {cart.cartItems.map((item) => (
                    <Col key={item.cartItemId} md={4} className="mb-4">
                        <Card>
                            <Card.Img variant="top" src={item.image} />
                            <Card.Body>
                                <Card.Title>{item.product.productName}</Card.Title>
                                <Card.Text>
                                    Price: ${item.product.price.toFixed(2)}
                                </Card.Text>

                                {/* Quantity input with arrows */}
                                <div className="d-flex align-items-center">
                                    <label htmlFor='quantity' className="me-2">Quantity:</label>
                                    <input
                                        id='quantity'
                                        name='quantity'
                                        type="number"
                                        min="1"
                                        value={quantities[item.cartItemId] ?? item.quantity}
                                        onChange={(e) => handleQuantityChange(item.cartItemId, e.target.value)}
                                        className="form-control me-2"
                                        style={{ width: "80px" }}
                                    />
                                    <Button
                                        variant="success"
                                        onClick={() => handleUpdate(item)}
                                        disabled={quantities[item.cartItemId] === item.quantity}
                                    >
                                        Update
                                    </Button>
                                </div>


                                <Button variant='danger' onClick={() => {
                                    setLoading(true);
                                    removeFromCart(item, loggedInUser);
                                    setCartVersion((prev) => prev + 1);
                                    }}>
                                    Remove
                                </Button>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
            <h3>Total: ${cart.total.toFixed(2)}</h3>
            <Button
                variant="primary"
                disabled={loading}
                onClick={!loading ? handleSubmit : null}
                >
                {loading ? 'Loading…' : 'Submit Order'}
            </Button>
        </Container>
    )
}