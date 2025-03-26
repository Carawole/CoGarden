import { useState, useEffect } from 'react'
import './App.css'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import NavBar from './components/NavBar';
import LoginForm from './components/LoginForm';
import LandingPage from './components/LandingPage';
import ProductList from './components/ProductList';
import ProductPage from './components/ProductPage';
import CartPage from './components/CartPage';
import { retrieveCart } from './components/CartContext';
import { Spinner } from 'react-bootstrap';


function App() {
  const [loggedInUser, setLoggedInUser] = useState(null)
	const [hasFinishedCheckingLocalStorage, setHasFinishedCheckingLocalStorage] = useState(false)
  const [cart, setCart] = useState([])
  const [loading, setLoading] = useState(false)
  const [cartItems, setCartItems] = useState([])
  const [categories, setCategories] = useState([
    { id: 1, title: "FLOWERS", name: "Flowers", img: "/images/electronics.jpg" },
    { id: 2, title: "TREES", name: "Trees", img: "/images/clothing.jpg" },
    { id: 3, title: "SHRUBS", name: "Shrubs", img: "/images/home-kitchen.jpg" },
    { id: 4, title: "EDIBLES", name: "Edibles", img: "/images/electronics.jpg" },
    { id: 5, title: "SUCCULENTS", name: "Succulents", img: "/images/clothing.jpg" },
    { id: 6, title: "OTHER", name: "Other", img: "/images/home-kitchen.jpg" }
  ])

  useEffect(() => {
    const storedUser = localStorage.getItem("loggedInUser");
    if (storedUser) {
      setLoggedInUser(JSON.parse(storedUser));
    }
    setHasFinishedCheckingLocalStorage(true);
  }, []);
  
  useEffect(() => {
    const fetchCart = async () => {
        setLoading(true);
        const cartData = await retrieveCart(loggedInUser);
        setCart(cartData);
        setLoading(false);
    };

    fetchCart();
}, [loggedInUser]);
  
  useEffect(() => {
    setCartItems(cart?.cartItems || []);
  }, [cart]);
  
  // Remove this in production
  useEffect(() => {
    console.log(cart);
    console.log(cartItems);
  }, [cart]);

	if (!hasFinishedCheckingLocalStorage) {
		return null
	}

  if (loading) {
    return (
      <Spinner animation="border" role="status">
        <span className="visually-hidden">Loading...</span>
      </Spinner>
    );
  }

  return (
    <Router>
      <NavBar loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser} cart={cart} categories={categories} />
      <div className='container'>
          <Routes>
              <Route path="/" element={ <LandingPage categories={categories}/> }/>
              <Route path="/category/:title" element={<ProductList categories={categories} loggedInUser={loggedInUser} cart={cart} setCart={setCart}/>} />
              <Route path="/product/:id" element={<ProductPage />} />
              <Route path="/cart" element={<CartPage cart={cart} loggedInUser={loggedInUser}/>} />
              <Route path="/login" element={<LoginForm setLoggedInUser={setLoggedInUser} />} />
          </Routes>
      </div>
    </Router>
  )
}

export default App
