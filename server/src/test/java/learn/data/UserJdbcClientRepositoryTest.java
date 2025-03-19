package learn.data;

import learn.TestHelper;
import learn.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcClientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    UserJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }


    @Nested
    class findByEmail {
        @Test
        void findByEmailSuccess() {
            User actual = repository.findByEmail("test@email.com");
            assertNotNull(actual);
            assertEquals(TestHelper.makeUser(), actual);
        }

        @Test
        void findByEmailFailure() {
            User actual = repository.findByEmail("notfound@missing.com");

            assertNull(actual);
        }
    }


    @Nested
    class Create {
        @Test
        void happyPath() {
            User toAdd = TestHelper.makeUser();
            toAdd.setEmail("unique@email.com");
            toAdd.setUserId(0);

            User actual = repository.create(toAdd);

            assertEquals(3, actual.getUserId());
        }

        @Test
        void throwsWhenEmailDuplicated() {
            User toAdd = TestHelper.makeUser();
            toAdd.setUserId(0);

            assertThrows(DuplicateKeyException.class, () -> repository.create(toAdd));
        }
    }
}