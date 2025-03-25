import { useState, useEffect } from 'react'
import './App.css'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import NavBar from './components/NavBar';
import LoginForm from './components/LoginForm';
import LandingPage from './components/LandingPage';
import CategoryPage from './components/CategoryPage';
import ProductPage from './components/ProductPage';
import CartPage from './components/CartPage';


function App() {
  const [loggedInUser, setLoggedInUser] = useState(null)
	const [hasFinishedCheckingLocalStorage, setHasFinishedCheckingLocalStorage] = useState(false)
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
		if (localStorage.getItem("loggedInUser") !== undefined) {
			setLoggedInUser(JSON.parse(localStorage.getItem("loggedInUser")))
		}
		setHasFinishedCheckingLocalStorage(true)
	}, [])

	if (!hasFinishedCheckingLocalStorage) {
		return null
	}

  return (
    <Router>
      <NavBar loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser} cartItems={cartItems} categories={categories} />
      <div className='container'>
          <Routes>
              <Route path="/" element={ <LandingPage categories={categories}/> }/>
              <Route path="/category/:id" element={<CategoryPage />} />
              <Route path="/product/:id" element={<ProductPage />} />
              <Route path="/cart" element={<CartPage />} />
              <Route path="/login" element={<LoginForm setLoggedInUser={setLoggedInUser} />} />
          </Routes>
      </div>
    </Router>
  )
}

export default App
