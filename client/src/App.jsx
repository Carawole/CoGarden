import { useState, useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import NavBar from './components/NavBar';
import LoginForm from './components/LoginForm';

function App() {
  const [loggedInUser, setLoggedInUser] = useState(null)
	const [hasFinishedCheckingLocalStorage, setHasFinishedCheckingLocalStorage] = useState(false)

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
      <div className='container'>
          <NavBar loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser} />
          <Routes>
              <Route path="/" element={<h1>Hello There</h1>} />
              <Route path="/login" element={<LoginForm setLoggedInUser={setLoggedInUser} />} />

          </Routes>
      </div>
    </Router>
  )
}

export default App
