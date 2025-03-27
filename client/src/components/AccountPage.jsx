import OrderList from "./OrderList";

const AccountPage = ({ loggedInUser ,setLoading, setCartVersion }) => {

    return (
        <div className="mt-5">
            <h1>Account Page</h1>

            <OrderList 
                loggedInUser={loggedInUser}
                setLoading={setLoading}
                setCartVersion={setCartVersion}
            />
        </div>
    );
};

export default AccountPage;
