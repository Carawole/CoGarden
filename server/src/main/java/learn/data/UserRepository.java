package learn.data;

import learn.models.User;

public interface UserRepository {

    public User findByEmail(String email);

    public User findById(int userId);

    public User create(User user);
}
