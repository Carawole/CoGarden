package learn.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Jwts;
import learn.domain.Result;
import learn.domain.ResultType;
import learn.domain.UserService;
import learn.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService service;

    private SecretSigningKey secretSigningKey;

    public UserController(UserService service, SecretSigningKey secretSigningKey) {
        this.service = service;
        this.secretSigningKey = secretSigningKey;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody User user) {
        Result<User> result = service.create(user);
        if (result.isSuccess()) {
            Map<String, String> output = createJwtFromUser(result.getPayload());
            return new ResponseEntity<>(output, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        Result<User> userResult = service.findByEmail(user.getEmail());

        if (userResult.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(userResult.getErrorMessages(), HttpStatus.NOT_FOUND);
        }

        char[] proposedPassword = user.getPasswordHash().toCharArray();
        char[] existingPassword = userResult.getPayload().getPasswordHash().toCharArray();

        if (BCrypt.verifyer().verify(proposedPassword, existingPassword).verified) {
            Map<String, String> jwtMap = createJwtFromUser(userResult.getPayload());
            return new ResponseEntity<>(jwtMap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(List.of("Email and password do not match"), HttpStatus.UNAUTHORIZED);
        }
    }

    private Map<String, String> createJwtFromUser(User user) {
        String jwt = Jwts.builder()
                .claim("email", user.getEmail())
                .claim("userId", user.getUserId())
                .signWith(secretSigningKey.getKey())
                .compact();
        Map<String, String> output = new HashMap<>();
        output.put("jwt", jwt);
        return output;
    }
}
