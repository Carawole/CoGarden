package learn.data;

import learn.models.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcClientRepository implements UserRepository{

    JdbcClient client;

    public UserJdbcClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public User findByEmail(String email) {
        final String sql = "select * from `user` where email = :email";

        return client.sql(sql)
                .param("email", email)
                .query(new UserMapper())
                .optional().orElse(null);
    }

    @Override
    public User create(User user) {
        final String sql = "insert into `user` (email, password_hash, is_admin) " +
                "values (:email, :passwordHash, :admin)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = client.sql(sql)
                .param("email", user.getEmail())
                .param("passwordHash", user.getPasswordHash())
                .param("admin", user.isAdmin())
                .update(keyHolder, "user_id");

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User findById(int userId) {
        return null;
    }


}
