package learn.domain;

import at.favre.lib.crypto.bcrypt.BCrypt;
import learn.data.UserJdbcClientRepository;
import learn.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final int BCRYPT_COST = 12;

    UserJdbcClientRepository repository;

    public UserService(UserJdbcClientRepository repository) {
        this.repository = repository;
    }

    public Result<User> findByEmail(String email) {
        Result<User> result = new Result<>();
        User foundUser = repository.findByEmail(email);

        if (foundUser == null) {
            result.addErrorMessage("User not found", ResultType.NOT_FOUND);
        } else {
            result.setPayload(foundUser);
        }

        return result;
    }

    public Result<User> create (User user) {
        Result<User> result = new Result<>();

        if (user.getEmail().isBlank()) {
            result.addErrorMessage("Email cannot be blank", ResultType.INVALID);
        }

        if (user.getPasswordHash().isBlank()) {
            result.addErrorMessage("Password cannot be blank", ResultType.INVALID);
        }

        if (repository.findByEmail(user.getEmail()) != null) {
            result.addErrorMessage("Email is already taken", ResultType.INVALID);
        }

        if (result.isSuccess()) {
            String hashedPassword = BCrypt.withDefaults().hashToString(BCRYPT_COST, user.getPasswordHash().toCharArray());

            user.setPasswordHash(hashedPassword);
            User createdUser = repository.create(user);
            result.setPayload(createdUser);
        }

        return result;
    }

}
