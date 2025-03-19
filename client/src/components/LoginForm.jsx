import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { jwtDecode } from "jwt-decode"

const LoginForm = ({ setLoggedInUser }) => {

    const navigate = useNavigate()

    const [user, setUser] = useState({
        email: "",
        passwordHash: ""
    })

    const [errors, setErrors] = useState([])

    const handleSubmit = (event) => {
        event.preventDefault()

        fetch("http://localhost:8080/api/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user)
        })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                response.json().then(fetchedUser => {
                    const user = jwtDecode(fetchedUser.jwt)
                    user.jwt = fetchedUser.jwt
                    setLoggedInUser(user)
                    localStorage.setItem("loggedInUser", JSON.stringify(user))
                    alert("You have successfully logged in!")
                    navigate("/")
                })
            } else {
                response.json().then(fetchedErrors => setErrors(fetchedErrors))
            }
        })

    }

    const handleChange = (event) => {
        setUser({ ...user, [event.target.name]: event.target.value })
    }

    return (
        <div className="row">
            {errors.length > 0 && <ul id="errors">
                {errors.map(error => <li key={error}>{error}</li>)}
            </ul>}

            <h2>Log into your account</h2>

            <div className="col-3"></div>
            <form onSubmit={handleSubmit} className="col-6">
                <div className="form-group">
                    <label htmlFor="email-input">Email: </label>
                    <input name="email" className="form-control" id="email-input" type="text" value={user.email} onChange={handleChange} />
                </div>

                <div className="form-group">
                    <label htmlFor="password-input">Password: </label>
                    <input name="passwordHash" className="form-control" id="password-input" type="text" value={user.passwordHash} onChange={handleChange} />
                </div>

                <div className="form-group">
                    <button className="btn btn-info" type="submit">Log in!</button>
                </div>
            </form>
        </div>
    )
}

export default LoginForm