package learn.data;

import learn.models.Cart;

import java.util.List;

public interface CartRepository {

    public List<Cart> findAll();

    public Cart retrieveByUserId(int userId);

    public boolean updateTotal(Cart cart);

    public boolean delete(Cart cart);

}
