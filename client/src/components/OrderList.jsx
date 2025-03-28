import React, { useEffect, useState } from 'react';
import { Container, Table, Badge, Spinner, Alert, Dropdown } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const OrderList = ({ loggedInUser, setLoading, setCartVersion }) => {
    const [orders, setOrders] = useState([]);
    const [error, setError] = useState(null);
    const [statusOptions] = useState(['PENDING', 'PROCESSING', 'CANCELLED', 'COMPLETED']);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchOrders = async () => {
            try { 
                const url = loggedInUser.isAdmin ? 'http://localhost:8080/api/order' : 'http://localhost:8080/api/order/myOrders';
                const response = await fetch(url, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': loggedInUser.jwt
                    }
                });

                if (!response.ok) throw new Error('Failed to fetch orders');

                const data = await response.json();
                setOrders(data);
            } catch (error) {
                setError(error.message);
            } finally {
                setCartVersion((prev) => prev + 1);
            }
        }

        fetchOrders();
    }, [loggedInUser]);

    const handleStatusChange = async (orderId, newStatus) => {
        try {
            const response = await fetch('http://localhost:8080/api/order', {
                method: 'PUT',
                headers: { 
                    'Content-Type': 'application/json',
                    'Authorization': loggedInUser.jwt
                 },
                body: JSON.stringify({ 
                    orderId: orderId,
                    status: newStatus
                })
            });

            if (!response.ok) {
                throw new Error('Failed to update status');
            }

            // Update the status locally
            setOrders((prevOrders) =>
                prevOrders.map((order) =>
                    order.orderId === orderId ? { ...order, status: newStatus } : order
                )
            );
        } catch (error) {
            console.error('Error updating status:', error);
        }
    };

    const getStatusColor = (status) => {
        switch (status) {
            case 'PROCESSING': return 'warning';
            case 'COMPLETED': return 'success';
            case 'CANCELLED': return 'danger';
            default: return 'secondary';
        }
    };

    if (error) {
        return <Alert variant="danger">{error}</Alert>;
    }

    return (
        <Container className="mt-5">
            <h2>{loggedInUser.isAdmin ? 'All Orders' : 'My Orders'}</h2>
            <Table striped bordered hover responsive>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        {loggedInUser.isAdmin && <th>User ID</th>}
                        <th>Total</th>
                        <th>Status</th>
                        {loggedInUser.isAdmin && <th>Actions</th>}
                    </tr>
                </thead>
                <tbody>
                    {orders.length > 0 ? (
                        orders.map((order) => (
                            <tr key={order.orderId}>
                                <td>{order.orderId}</td>
                                {loggedInUser.isAdmin && <td>{order.userId}</td>}
                                <td>{order.total.toFixed(2)}</td>
                                <td>
                                    <Badge bg={getStatusColor(order.status)}>
                                        {order.status}
                                    </Badge>
                                </td>
                                {loggedInUser.isAdmin && (
                                    <td>
                                        <Dropdown onSelect={(newStatus) => handleStatusChange(order.orderId, newStatus)}>
                                            <Dropdown.Toggle variant="outline-secondary" size="sm">
                                                Change Status
                                            </Dropdown.Toggle>
                                            <Dropdown.Menu>
                                                {statusOptions.map((status) => (
                                                    <Dropdown.Item key={status} eventKey={status}>
                                                        {status}
                                                    </Dropdown.Item>
                                                ))}
                                            </Dropdown.Menu>
                                        </Dropdown>
                                    </td>
                                )}
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan={loggedInUser.isAdmin ? 4 : 3} className="text-center">
                                No orders found.
                            </td>
                        </tr>
                    )}
                </tbody>
            </Table>
        </Container>
    );


};

export default OrderList;