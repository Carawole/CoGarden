import OrderList from "./OrderList";

const AccountPage = ({ loggedInUser }) => {

    return (
        <div className="mt-5">
            <h1>Account Page</h1>

            <OrderList 
                loggedInUser={loggedInUser}
            />
        </div>
    );
};

export default AccountPage;
