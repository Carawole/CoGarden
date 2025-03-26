import { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button, Spinner, Modal } from 'react-bootstrap';
import { submitOrder, removeFromCart } from './CartContext';

export default function CartPage({ cart, loggedInUser, setCartVersion }) {

    const [isLoading, setLoading] = useState(false);

    useEffect(() => {
      function simulateNetworkRequest() {
        return new Promise(resolve => {
          setTimeout(resolve, 2000);
        });
      }
  
      if (isLoading) {
        simulateNetworkRequest().then(() => {
          setLoading(false);
        });
      }
    }, [isLoading]);
  
    const handleSubmit = () => {
        submitOrder(cart, loggedInUser);
        setCartVersion((prev) => prev + 1);
        setLoading(true);
    }

    const handleRemoveClick = () => {


    }

    if (isLoading) {
            return (
                <Container className="text-center mt-5">
                    <Spinner animation="border" />
                </Container>
            );
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
                                <Card.Text>
                                    Quantity: {item.quantity}
                                </Card.Text>
                                <Button variant='danger' onClick={handleRemoveClick}>
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
                disabled={isLoading}
                onClick={!isLoading ? handleSubmit : null}
                >
                {isLoading ? 'Loading…' : 'Submit Order'}
            </Button>
        </Container>
    )
}