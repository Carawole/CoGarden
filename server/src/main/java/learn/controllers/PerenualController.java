package learn.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import learn.domain.PerenualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/perenual")
public class PerenualController {

    private final SecretSigningKey secretSigningKey;

    @Autowired
    private PerenualService perenualService;

    public PerenualController(SecretSigningKey secretSigningKey) {
        this.secretSigningKey = secretSigningKey;
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchPlants(@RequestParam String query, @RequestHeader Map<String, String> headers) {
        Boolean isAdmin = confirmAdmin(headers);
        if (isAdmin) {
            return perenualService.searchPlants(query);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<Object> getPlantDetails(@RequestParam int id, @RequestHeader Map<String, String> headers) {
        Boolean isAdmin = confirmAdmin(headers);
        if (isAdmin) {
            return perenualService.getPlantDetails(id);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
