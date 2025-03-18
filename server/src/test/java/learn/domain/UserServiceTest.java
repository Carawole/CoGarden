package learn.domain;

import learn.TestHelper;
import learn.data.UserJdbcClientRepository;
import learn.models.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserJdbcClientRepository repository;

    @Nested
    class findByEmail {
        @Test
        void happyPath() {
            User user = TestHelper.makeUser();
            when(repository.findByEmail(user.getEmail())).thenReturn(user);
            Result<User> expected = new Result<>();

            expected.setPayload(user);

            Result<User> actual = service.findByEmail(user.getEmail());

            assertEquals(expected, actual);

        }

        @Test
        void doesNotFind() {

            String email = "email.com";
            when(repository.findByEmail(email)).thenReturn(null);
            Result<User> expected = new Result<>();
            expected.addErrorMessage("User not found", ResultType.NOT_FOUND);

            Result<User> actual = service.findByEmail(email);

            assertEquals(expected, actual);
        }
    }


    @Nested
    class Create {
        @Test
        void happyPath() {
            User toAdd = TestHelper.makeUser();
            toAdd.setUserId(0);
            User afterAdd = TestHelper.makeUser();
            when(repository.create(toAdd)).thenReturn(afterAdd);
            Result<User> expected = new Result<>();
            expected.setPayload(afterAdd);

            Result<User> actual = service.create(toAdd);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenEmailBlank() {
            User toAdd = TestHelper.makeUser();
            toAdd.setUserId(0);
            toAdd.setEmail("");
            Result<User> expected = new Result<>();
            expected.addErrorMessage("Email cannot be blank", ResultType.INVALID);

            Result<User> actual = service.create(toAdd);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenPasswordHashBlank() {
            User toAdd = TestHelper.makeUser();
            toAdd.setUserId(0);
            toAdd.setPasswordHash("");
            Result<User> expected = new Result<>();
            expected.addErrorMessage("Password cannot be blank", ResultType.INVALID);

            Result<User> actual = service.create(toAdd);

            assertEquals(expected, actual);
        }


        @Test
        void failsWhenEmailDuplicated() {
            User toAdd = TestHelper.makeUser();
            toAdd.setUserId(0);
            when(repository.findByEmail(toAdd.getEmail())).thenReturn(toAdd);
            Result<User> expected = new Result<>();
            expected.addErrorMessage("Email is already taken", ResultType.INVALID);

            Result<User> actual = service.create(toAdd);

            assertEquals(expected, actual);
        }

//        @Test
//        void setsAdminToFalseIfNotExplicitlyTrue() {
//            User toAdd = TestHelper.makeUser();
//            toAdd.setUserId(0);
//            toAdd.setAdmin(null);
//            User afterAdd = TestHelper.makeUser();
//            when(repository.create(toAdd)).thenReturn(afterAdd);
//            Result<User> expected = new Result<>();
//            expected.setPayload(afterAdd);
//
//            Result<User> actual = service.create(toAdd);
//
//            assertEquals(expected, actual);
//        }
    }
}