package learn.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import learn.domain.CartService;
import learn.domain.Result;
import learn.models.Cart;
import learn.models.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;

    private final SecretSigningKey secretSigningKey;

    public CartController(CartService service, SecretSigningKey secretSigningKey) {
        this.service = service;
        this.secretSigningKey = secretSigningKey;
    }

    @GetMapping
    public ResponseEntity<Object> retrieveCartByUserId(@RequestHeader Map<String, String> headers) {
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return ResponseEntity.ok(service.retrieveCartByUserId(userId));
        }
    }

    @GetMapping("/allCarts")
    public ResponseEntity<Object> findAll(@RequestHeader Map<String, String> headers) {
        Boolean isAdmin = confirmAdmin(headers);
        if (isAdmin) {
            Result<List<Cart>> result = service.findAll();
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addToCart(@RequestBody CartItem cartItem, @RequestHeader Map<String, String> headers) {
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            Result<Cart> result = service.addToCart(cartItem);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<Object> submitOrder(@RequestBody Cart cart, @RequestHeader Map<String, String> headers) {
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            boolean result = service.submitOrder(cart);
            if (result) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateCartItemQuantity(@RequestBody CartItem cartItem, @RequestHeader Map<String, String> headers) {
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            Result<Cart> result = service.updateCartItemQuantity(cartItem);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> removeFromCart(@RequestBody CartItem cartItem, @RequestHeader Map<String, String> headers) {
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            Result<Cart> result = service.removeFromCart(cartItem);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    private Integer getUserIdFromHeaders(Map<String, String> headers) {
        if (headers.get("authorization") == null) {
            return null;
        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretSigningKey.getKey())
                    .build().parseClaimsJws(headers.get("authorization"));
            return (Integer) claims.getBody().get("userId");
        } catch (Exception e) {
            return null;
        }
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
