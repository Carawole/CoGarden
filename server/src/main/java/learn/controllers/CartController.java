package learn.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import learn.domain.CartService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
