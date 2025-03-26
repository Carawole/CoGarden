import { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button, Spinner, Modal } from 'react-bootstrap';

export default function CartPage({ cart, loggedInUser }) {

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
  
    const handleClick = () => setLoading(true);

    return (
        <Container className="mt-5">
            <h1>Cart</h1>
            <Row>
                {cart.cartItems.map((item) => (
                    <Col key={item.id} md={4} className="mb-4">
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
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
            <h3>Total: ${cart.total.toFixed(2)}</h3>
            <Button
                variant="primary"
                disabled={isLoading}
                onClick={!isLoading ? handleClick : null}
                >
                {isLoading ? 'Loading…' : 'Submit Order'}
            </Button>
        </Container>
    )
}