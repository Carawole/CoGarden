package learn.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import learn.domain.OrderService;
import learn.domain.Result;
import learn.models.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService service;

    private final SecretSigningKey secretSigningKey;

    public OrderController(OrderService service, SecretSigningKey secretSigningKey) {
        this.service = service;
        this.secretSigningKey = secretSigningKey;
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader Map<String, String> headers) {
        Boolean isAdmin = confirmAdmin(headers);
        if (isAdmin) {
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Object> findByOrderId(@PathVariable int orderId, @RequestHeader Map<String, String> headers) {
        Boolean isAdmin = confirmAdmin(headers);
        if (isAdmin) {
             Result<Order> result= service.findByOrderId(orderId);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/myOrders")
    public ResponseEntity<Object> findByUserId(@RequestHeader Map<String, String> headers) {
        Integer userId = getUserIdFromHeaders(headers);
        if (userId != null) {
            Result<List<Order>> result = service.findByUserId(userId);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    public ResponseEntity<Object> updateStatus(@RequestBody Order order, @RequestHeader Map<String, String> headers) {
        Boolean isAdmin = confirmAdmin(headers);
        if (isAdmin) {
             Result<Boolean> result = service.updateStatus(order);
            if (result.isSuccess()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
