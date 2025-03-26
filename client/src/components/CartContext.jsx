export async function retrieveCart( loggedInUser ) {

    if (!loggedInUser || !loggedInUser.jwt) {
        console.warn("No JWT found, skipping cart fetch.");
        return;  // Skip API call if no jwt
    }
    
    try {
        const response = await fetch("http://localhost:8080/api/cart", {
            method: "GET",
            headers: {
                'Content-Type': "application/json",
                'Authorization': loggedInUser.jwt
            }
        });

        if (!response.ok) {
            throw new Error(`Failed to fetch cart: ${response.status} ${response.statusText}`);
        }

        const data = await response.json();
        
        console.log("Cart Data:", data);  // Debug log

        // Return the payload containing the cart items
        return data.payload;

    } catch (error) {
        console.error("Failed to retrieve cart:", error.message);
        return [];  // Return an empty cart on error
    }
}

export async function addToCart( loggedInUser, cart, product ) {

    try {
        const response = await fetch(`http://localhost:8080/api/cart/add`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': loggedInUser.jwt
            },
            body: JSON.stringify(
                {
                    cartId: cart.cartId,
                    product: product,
                    quantity: 1
                }
            )
        });

        if (!response.ok) {
            throw new Error(`Failed to add product to cart: ${response.status} ${response.statusText}`);
        }

        const data = await response.json();

        console.log("Cart Data:", data);  // Debug log
        return data.payload;

        
    } catch (error) {
        console.error('Error:', error.message);
        return [];
    }




    // fetch(`http://localhost:8080/api/cart/add`, {
    //     method: 'POST',
    //     headers: {
    //       'Content-Type': 'application/json',
    //       'Authorization': loggedInUser.jwt
    //     },
    //     body: JSON.stringify(
    //         {
    //             cartId: cart.cartId,
    //             product: product,
    //             quantity: 1
    //         }
    //     )
    // })
    // .then((response) => response.json())
    // .then((data) => {
    //     console.log("Cart Data:", data);  // Debug log
    //     return data.payload;
    // })
    // .catch((error) => {
    //     console.error('Error:', error);
    //     return [];
    // });
}

export default retrieveCart;