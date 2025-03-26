import { Carousel, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom"
import "../styles/LandingPage.css";

const LandingPage = ({ categories }) => {
    const navigate = useNavigate();

    const handleCategoryClick = (categoryTitle) => {
        navigate(`/category/${categoryTitle}`);  // Navigate to product list for selected category
    };

    return (
        <div className="carousel-container">
            <Carousel>
                {categories.map((category) => (
                    <Carousel.Item key={category.id}>
                        <img
                            className="d-block w-100"
                            src={category.img}
                            alt={category.name}
                            style={{ height: "500px", objectFit: "cover" }}
                        />
                        <Carousel.Caption>
                            <h3>{category.title}</h3>
                            <p>Explore the latest in {category.name}!</p>
                            <Button 
                                variant="primary" 
                                onClick={() => handleCategoryClick(category.title)}
                            >
                                Shop Now
                            </Button>
                        </Carousel.Caption>
                    </Carousel.Item>
                ))}
            </Carousel>
        </div>
    );
};

export default LandingPage