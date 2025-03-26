import { Link, NavLink } from 'react-router-dom';
import logo from '../assets/react.svg';
import SearchBar from './SearchBar';
import { useState } from 'react';
import { FaShoppingCart } from 'react-icons/fa';
import "../styles/NavBar.css";
import { Badge } from 'react-bootstrap';

const NavBar = ({ loggedInUser, setLoggedInUser, cart, categories }) => {
    return (
		<nav className="navbar sticky-top navbar-light bg-light inline-flex">
			<NavLink className="navbar-brand" to="/">
				<img src={logo} alt="CoGarden" width="100" />
			</NavLink>
			<SearchBar categories={categories} />

			<ul className='navbar-nav'>
			{ loggedInUser === null ? 
				<>
					<li className='nav-item'>
						<NavLink className={(arg) => {
							if (arg.isActive) {
								return 'nav-link custom-active'
							} else {
								return 'nav-link'
							}
						}} to="/login">Log In/Create An Account</NavLink>
					</li>
				</> : <>
					<li className='nav-item'>
						<button className='nav-link' onClick={() => {
							setLoggedInUser(null)
							localStorage.clear("loggedInUser")	
						}}>Log Out</button>
					</li>
					<li className='nav-item'>
						<NavLink className={(arg) => {
							if (arg.isActive) {
								return 'nav-link custom-active'
							} else {
								return 'nav-link'
							}
						}} to="/account">View Account</NavLink>
					</li>
					<li className='nav-item'>
						{/* Cart Icon with Item Count */}
						<Link to="/cart" className="nav-link cart-icon">
							<FaShoppingCart size={24} />
							{cart && cart.cartItems.length >= 0 && (
								<Badge bg="danger" className="cart-badge">
									{cart.cartItems.length}
								</Badge>
							)}
						</Link>
					</li>
				</>
			}
			</ul>

		</nav>
        // <header className='mb-3'>
		// 		<nav className='navbar sticky-top navbar-light bg-light '>
		// 			<div className='d-flex'>
		// 				<NavLink className='navbar-brand' to='/'>
		// 					<img src={logo} alt='Solar Farm' width='150' />
		// 				</NavLink>
		// 				<ul className='navbar-nav'>
		// 					<li className='nav-item'>
		// 						<NavLink className={(arg) => {
		// 							if (arg.isActive) {
		// 								return 'nav-link custom-active'
		// 							} else {
		// 								return 'nav-link'
		// 							}
		// 						}} to='/'>
		// 							Home
		// 						</NavLink>
		// 					</li>
		// 					<li className='nav-item'>
		// 						<NavLink className={(arg) => {
		// 							if (arg.isActive) {
		// 								return 'nav-link custom-active'
		// 							} else {
		// 								return 'nav-link'
		// 							}
		// 						}} to='/'>
		// 							About
		// 						</NavLink>
		// 					</li>
		// 					<li className='nav-item'>
		// 						<NavLink className={(arg) => {
		// 							if (arg.isActive) {
		// 								return 'nav-link custom-active'
		// 							} else {
		// 								return 'nav-link'
		// 							}
		// 						}} to='/'>
		// 							Contact
		// 						</NavLink>
		// 					</li>
		// 					<li className='nav-item'>
		// 						<NavLink className={(arg) => {
		// 							if (arg.isActive) {
		// 								return 'nav-link custom-active'
		// 							} else {
		// 								return 'nav-link'
		// 							}
		// 						}} to="/list">View All Panels</NavLink>
		// 					</li>
		////////////////////////////////////////////////////////////////////////////////////
		// 					{ loggedInUser === null ? 
		// 						<>
		// 							<li className='nav-item'>
		// 								<NavLink className={(arg) => {
		// 									if (arg.isActive) {
		// 										return 'nav-link custom-active'
		// 									} else {
		// 										return 'nav-link'
		// 									}
		// 								}} to="/signup">Create an Account</NavLink>
		// 							</li>
		// 							<li className='nav-item'>
		// 								<NavLink className={(arg) => {
		// 									if (arg.isActive) {
		// 										return 'nav-link custom-active'
		// 									} else {
		// 										return 'nav-link'
		// 									}
		// 								}} to="/login">Log In</NavLink>
		// 							</li>
		// 						</> : <>
		// 							<li className='nav-item'>
		// 								<button className='nav-link' onClick={() => {
		// 									setLoggedInUser(null)
		// 									localStorage.clear("loggedInUser")	
		// 								}}>Log Out</button>
		// 							</li>
		// 							<li className='nav-item'>
		// 								<NavLink className={(arg) => {
		// 									if (arg.isActive) {
		// 										return 'nav-link custom-active'
		// 									} else {
		// 										return 'nav-link'
		// 									}
		// 								}} to="/myPanels">View My Panels</NavLink>
		// 							</li>
		// 							<li className='nav-item'>
		// 								<NavLink className={(arg) => {
		// 									if (arg.isActive) {
		// 										return 'nav-link custom-active'
		// 									} else {
		// 										return 'nav-link'
		// 									}
		// 								}} to="/add">Add a Panel</NavLink>
		// 							</li>
		// 						</>
		// 					}
		// 				</ul>
		// 			</div>
		// 		</nav>
		// 	</header>
    )
}

export default NavBar