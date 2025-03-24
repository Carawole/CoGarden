package learn.domain;

import learn.data.ProductJdbcClientRepository;
import learn.models.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    final ProductJdbcClientRepository repository;

    public ProductService(ProductJdbcClientRepository repository) {
        this.repository = repository;
    }

    public Result<List<Product>> findAll() {
        Result<List<Product>> result = new Result<>();
        result.setPayload(repository.findAll());
        return result;
    }

    public Result<List<Product>> findByCategory( String category) {
        Result<List<Product>> result = new Result<>();

        if (category == null || category.isBlank()) {
            result.addErrorMessage("Category is required", ResultType.INVALID);
        }

        if(!result.isSuccess()){
            return result;
        }
        result.setPayload(repository.findByCategory(category));

        return result;
    }

    public Result<List<Product>> findByName(String productName, String category) {
        Result<List<Product>> result = new Result<>();

        if (productName == null || productName.isBlank()) {
            result.addErrorMessage("Product name is required", ResultType.INVALID);
        }

        if(!result.isSuccess()){
            return result;
        }

        result.setPayload(repository.findByName(productName, category));
        return result;
    }

    public Result<Product> create(Product product) {
        Result<Product> result = validate(product);
        if (!result.isSuccess()) {
            return result;
        }

        if (product.getProductId() != 0) {
            result.addErrorMessage("Product id cannot be set for `create` operation", ResultType.INVALID);
            return result;
        }

        result.setPayload(repository.create(product));
        return result;
    }

    public Result<Product> update(Product product) {

        Result<Product> result = validate(product);
        if (!result.isSuccess()) {
            return result;
        }

        if (product.getProductId() <= 0 ) {
            result.addErrorMessage("Product id is required", ResultType.INVALID);
            return result;
        }

        if (!repository.update(product)) {
            result.addErrorMessage("Product not found", ResultType.NOT_FOUND);
            return result;
        }

        return result;
    }

    private Result<Product> validate(Product product) {
        Result<Product> result = new Result<>();
        if (product == null) {
            result.addErrorMessage("Product is required", ResultType.INVALID);
            return result;
        }

        if (product.getProductName() == null || product.getProductName().isBlank()) {
            result.addErrorMessage("Product name is required", ResultType.INVALID);
        }

        if (product.getCategory() == null || product.getCategory().getName().isBlank()) {
            result.addErrorMessage("Category is required", ResultType.INVALID);
        }

        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            result.addErrorMessage("Price must be greater than 0.00", ResultType.INVALID);
        }

        return result;
    }

}
