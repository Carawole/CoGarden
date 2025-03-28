package learn.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import learn.domain.ProductService;
import learn.domain.Result;
import learn.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    private final SecretSigningKey secretSigningKey;

    public ProductController(ProductService service, SecretSigningKey secretSigningKey) {
        this.service = service;
        this.secretSigningKey = secretSigningKey;
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader Map<String, String> headers) {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(params = "category")
    public ResponseEntity<Object> findByCategory(@RequestParam String category) {
        return new ResponseEntity<>(service.findByCategory(category), HttpStatus.OK);
    }

    @GetMapping(params = {"productName", "category"})
    public ResponseEntity<Object> findByName(@RequestParam String productName, @RequestParam String category) {
        return new ResponseEntity<>(service.findByName(productName, category), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Product product, @RequestHeader Map<String, String> headers) {
        Boolean isAdmin = confirmAdmin(headers);
        if (isAdmin) {
            Result<Product> result = service.create(product);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Product product, @RequestHeader Map<String, String> headers) {
        Boolean isAdmin = confirmAdmin(headers);
        if (isAdmin) {
            product.setProductId(id);
            Result<Product> result = service.update(product);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private Boolean confirmAdmin(Map<String, String> headers) {
        if (headers.get("authorization") == null) {
            return false;
        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretSigningKey.getKey())
                    .build().parseClaimsJws(headers.get("authorization"));
            return (Boolean) claims.getBody().get("isAdmin");
        } catch (Exception e) {
            return false;
        }
    }

}
