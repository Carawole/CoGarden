package learn.models;

import java.util.Objects;

public class User {

    private int userId;
    private String email;
    private String passwordHash;
    private boolean isAdmin;

    public User() {
    }

    public User(int userId, String email, String passwordHash, boolean isAdmin) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && isAdmin == user.isAdmin && Objects.equals(email, user.email) && Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, passwordHash, isAdmin);
    }
}
