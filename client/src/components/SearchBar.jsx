import React, { useState } from 'react';
import { Form, FormControl, Button, Dropdown, ToggleButton, ToggleButtonGroup } from 'react-bootstrap';

const SearchBar = ({ categories, loggedInUser, onSearch }) => {
    const [query, setQuery] = useState('');
    const [category, setCategory] = useState('all');
    const [searchType, setSearchType] = useState('internal');  // 'internal' or 'perenual'

    const handleSearch = () => {
        onSearch(query, category, searchType);
        setQuery('');
    };

    return (
        <Form className="d-flex align-items-center">
            <FormControl
                type="text"
                placeholder="Search"
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                className="me-2"
            />

            <Dropdown>
                <Dropdown.Toggle variant="outline-secondary">
                    {category === 'all' ? 'All Categories' : category}
                </Dropdown.Toggle>
                <Dropdown.Menu>
                    <Dropdown.Item onClick={() => setCategory('all')}>All</Dropdown.Item>
                    {categories.map((cat) => (
                        <Dropdown.Item key={cat.id || cat.name} onClick={() => setCategory(cat.name)}>
                            {cat.name}
                        </Dropdown.Item>
                    ))}
                </Dropdown.Menu>
            </Dropdown>

            { loggedInUser ? (
            loggedInUser?.isAdmin && (
                <ToggleButtonGroup
                    type="radio"
                    name="searchType"
                    value={searchType}
                    onChange={setSearchType}
                    className="ms-3"
                >
                    <ToggleButton id="tbg-radio-1" value="internal" variant="outline-primary">
                        Internal
                    </ToggleButton>
                    <ToggleButton id="tbg-radio-2" value="perenual" variant="outline-danger">
                        Perenual
                    </ToggleButton>
                </ToggleButtonGroup>
            )) : null
            }

            <Button variant={searchType == 'internal' ? "primary" : "danger"} onClick={handleSearch} className="ms-2">
                Search
            </Button>
        </Form>
    );
};

export default SearchBar;
