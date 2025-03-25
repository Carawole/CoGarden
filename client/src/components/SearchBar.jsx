import { useState } from 'react';

const SearchBar = ({ categories }) => {
    const [searchTerm, setSearchTerm] = useState("");
    const [selectedCategory, setSelectedCategory] = useState("all");

    const handleSearch = () => {
        console.log(`Searching for: ${searchTerm} in category: ${selectedCategory}`);
        // Call API or filter products
    };

    return (
        <div className="search-bar d-flex">
            <select 
                className="form-control mr-2" 
                value={selectedCategory}
                onChange={(e) => setSelectedCategory(e.target.value)}
            >
                <option value="all">All Categories</option>
                {categories.map((cat) => (
                    <option key={cat.id} value={cat.id}>
                        {cat.name}
                    </option>
                ))}
            </select>

            <input
                className="form-control"
                type="search"
                placeholder="Search"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />

            <button 
                className="btn btn-primary ml-2"
                onClick={handleSearch}
            >
                Search
            </button>
        </div>
    );
};

export default SearchBar;
