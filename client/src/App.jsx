import { useState, useEffect } from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import NavBar from './components/NavBar';
import LoginForm from './components/LoginForm';
import LandingPage from './components/LandingPage';
import ProductList from './components/ProductList';
import CartPage from './components/CartPage';
import AccountPage from './components/AccountPage';
import AddProductForm from './components/AddProductForm';
import SearchResults from './components/SearchResults';
import { retrieveCart } from './components/CartContext';
import { Spinner } from 'react-bootstrap';
import trees from './assets/trees.jpg';
import others from './assets/others.jpg';
import shrubs from './assets/shrubs.jpg';
import edibles from './assets/edibles.jpg';
import flowers from './assets/flowers.jpg';
import aloe from './assets/Aloe.jpg';

function App() {
  const [loggedInUser, setLoggedInUser] = useState(null);
  const [hasFinishedCheckingLocalStorage, setHasFinishedCheckingLocalStorage] = useState(false);
  const [cart, setCart] = useState([]);
  const [cartVersion, setCartVersion] = useState(0);
  const [loading, setLoading] = useState(false);
  const [categories, setCategories] = useState([
    { id: 1, title: "FLOWERS", name: "Flowers", img: flowers },
    { id: 2, title: "TREES", name: "Trees", img: trees },
    { id: 3, title: "SHRUBS", name: "Shrubs", img: shrubs },
    { id: 4, title: "EDIBLES", name: "Edibles", img: edibles },
    { id: 5, title: "SUCCULENTS", name: "Succulents", img: aloe },
    { id: 6, title: "OTHER", name: "Other", img: others },
  ]);

  // Fetch user from local storage on mount
  useEffect(() => {
    const storedUser = localStorage.getItem("loggedInUser");
    if (storedUser) {
      setLoggedInUser(JSON.parse(storedUser));
    }
    setHasFinishedCheckingLocalStorage(true);
  }, []);

  // Fetch cart data when the app loads or user changes cart
  useEffect(() => {
    const fetchCart = async () => {
      try {
        const cartData = await retrieveCart(loggedInUser);
        setCart(cartData);
      } catch (error) {
        console.error('Failed to load cart:', error);
      } finally {
        // Ensure spinner shows for at least 500ms
        setTimeout(() => {
          setLoading(false);
        }, 500);
      }
    };

    if (loggedInUser) {
      fetchCart();
    }
  }, [loggedInUser, cartVersion]);

  if (!hasFinishedCheckingLocalStorage) {
    return null;
  }

  // Display spinner while loading
  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
      </div>
    );
  }

  return (
    <Router>
      <NavBar loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser} cart={cart} categories={categories} setLoading={setLoading} setCartVersion={setCartVersion} />
      <div className='container'>
        <Routes>
          <Route path="/" element={<LandingPage categories={categories} />} />
          <Route 
            path="/category/:title" 
            element={<ProductList 
              categories={categories} 
              loggedInUser={loggedInUser} 
              cart={cart} 
              setLoading={setLoading}
              setCartVersion={setCartVersion} 
            />} 
          />
          <Route path="/search-results" element={<SearchResults loggedInUser={loggedInUser} setLoading={setLoading} setCartVersion={setCartVersion} />} />
          <Route 
            path="/cart" 
            element={<CartPage 
              cart={cart} 
              loggedInUser={loggedInUser} 
              setLoading={setLoading}
              setCartVersion={setCartVersion} 
            />} 
          />
          <Route 
            path="/login" 
            element={<LoginForm 
              setLoggedInUser={setLoggedInUser} 
              cart={cart} 
              setLoading={setLoading}
              setCartVersion={setCartVersion} 
            />} 
          />
          <Route 
            path="/add-product" 
            element={<AddProductForm 
              loggedInUser={loggedInUser} 
              categories={categories} 
              setLoading={setLoading}
              setCartVersion={setCartVersion} 
            />} 
          />
          <Route 
            path="/account" 
            element={<AccountPage 
              loggedInUser={loggedInUser} 
              setLoading={setLoading}
              setCartVersion={setCartVersion} 
            />} 
          />
          <Route path='*' element={<Navigate to='/' />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
