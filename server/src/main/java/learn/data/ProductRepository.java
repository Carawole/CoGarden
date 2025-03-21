package learn.data;

import learn.models.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    List<Product> findByCategory(String category);

    List<Product> findByName(String name, String category);

    Product findById(int id);

    Product create(Product product);

    boolean update(Product product);

    boolean deleteById(int id);
}
