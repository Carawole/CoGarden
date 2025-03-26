import { Link, NavLink, useNavigate } from 'react-router-dom';
import logo from '../assets/react.svg';
import SearchBar from './SearchBar';
import { FaShoppingCart } from 'react-icons/fa';
import "../styles/NavBar.css";
import { Badge } from 'react-bootstrap';

const NavBar = ({ loggedInUser, setLoggedInUser, cart, categories }) => {
    const navigate = useNavigate();

    const handleSearch = (query, category, searchType) => {
        const params = new URLSearchParams();
        params.append('query', query);
        params.append('category', category);
        params.append('source', searchType);

        navigate(`/search-results?${params.toString()}`);
    };

    return (
		<nav className="navbar sticky-top navbar-light bg-light inline-flex">
			<NavLink className="navbar-brand" to="/">
				<img src={logo} alt="CoGarden" width="100" />
			</NavLink>

            {/* Pass the loggedInUser and handleSearch to SearchBar */}
			<SearchBar 
                categories={categories} 
                loggedInUser={loggedInUser} 
                onSearch={handleSearch} 
            />

			<ul className='navbar-nav'>
			{ loggedInUser === null ? 
				<>
					<li className='nav-item'>
						<NavLink className={(arg) => arg.isActive ? 'nav-link custom-active' : 'nav-link'} 
                            to="/login">Log In/Create An Account
                        </NavLink>
					</li>
				</> : <>
					<li className='nav-item'>
						<button className='nav-link' onClick={() => {
							setLoggedInUser(null)
							localStorage.clear("loggedInUser")	
						}}>Log Out</button>
					</li>
					<li className='nav-item'>
						<NavLink className={(arg) => arg.isActive ? 'nav-link custom-active' : 'nav-link'} 
                            to="/account">View Account
                        </NavLink>
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
    )
}

export default NavBar;
