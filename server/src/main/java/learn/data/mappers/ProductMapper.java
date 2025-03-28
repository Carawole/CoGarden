package learn.data.mappers;

import learn.models.Category;
import learn.models.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException{
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setCategory(Category.fromString(rs.getString("category")));
        product.setDescription(rs.getString("description"));
        product.setCycle(rs.getString("cycle"));
        product.setWatering(rs.getString("watering"));
        product.setSunlight(rs.getString("sunlight"));
        product.setHardinessZone(rs.getInt("hardiness_zone"));
        return product;
    }
}
