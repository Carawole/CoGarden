package learn.data;

import learn.data.mappers.ProductMapper;
import learn.models.Product;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductJdbcClientRepository implements ProductRepository{

    final JdbcClient client;
    private final String SELECT = """
            select product_id, product_name, category, `description`, cycle, watering, sunlight, hardiness_zone, price
            from product
            """;

    public ProductJdbcClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Product create(Product product) {
        final String sql = """
                insert into product (product_name, category, `description`, cycle, watering, sunlight, hardiness_zone, price)
                values (:product_name, :category, :description, :cycle, :watering, :sunlight, :hardiness_zone, :price);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = client.sql(sql)
                .param("product_name", product.getProductName())
                .param("category", product.getCategory().toString())
                .param("description", product.getDescription())
                .param("cycle", product.getCycle())
                .param("watering", product.getWatering())
                .param("sunlight", product.getSunlight())
                .param("hardiness_zone", product.getHardinessZone())
                .param("price", product.getPrice())
                .update(keyHolder, "product_id");


        if (rowsAffected <= 0) {
            return null;
        }

        product.setProductId(keyHolder.getKey().intValue());
        return product;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public List<Product> findAll() {
        return client.sql(SELECT + ";").query(new ProductMapper()).list();
    }

    @Override
    public List<Product> findByCategory(String category) {
        return client.sql(SELECT + "where category = :category;").param("category", category).query(new ProductMapper()).list();
    }

    @Override
    public Product findById(int id) {
        return null;
    }

    @Override
    public List<Product> findByName(String productName, String category) {
        if (category == null || category.isBlank()) {
            return client.sql(SELECT + "where product_name like :product_name limit 5;").param("product_name", "%" + productName + "%").query(new ProductMapper()).list();
        } else {
            return client.sql(SELECT + "where product_name like :product_name AND category = :category limit 5;").param("product_name", "%" + productName + "%").param("category", category).query(new ProductMapper()).list();
        }
    }

    @Override
    public boolean update(Product product) {
        final String sql = """
                update product set
                    product_name = :product_name,
                    category = :category,
                    `description` = :description,
                    cycle = :cycle,
                    watering = :watering,
                    sunlight = :sunlight,
                    hardiness_zone = :hardiness_zone,
                    price = :price
                where product_id = :product_id;
                """;

        return client.sql(sql)
                .param("product_name", product.getProductName())
                .param("category", product.getCategory().toString())
                .param("description", product.getDescription())
                .param("cycle", product.getCycle())
                .param("watering", product.getWatering())
                .param("sunlight", product.getSunlight())
                .param("hardiness_zone", product.getHardinessZone())
                .param("price", product.getPrice())
                .param("product_id", product.getProductId())
                .update() > 0;
    }
}
