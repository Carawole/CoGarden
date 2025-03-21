package learn.data;

import learn.TestHelper;
import learn.models.Category;
import learn.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductJdbcClientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    ProductJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }

    @Test
    void shouldFindAll() {
        List<Product> products = repository.findAll();

        assertNotNull(products);
        assertEquals(3, products.size());
    }

    @Nested
    class findById {
        @Test
        void findByIdSuccess() {
            Product actual = repository.findById(1);
            assertNotNull(actual);
            assertEquals("Test Product 1", actual.getProductName());
        }

        @Test
        void findByIdFailure() {
            Product actual = repository.findById(999);

            assertNull(actual);
        }
    }

    @Nested
    class findByName {
        @Test
        void findByNameAndCategory() {
            List<Product> actual = repository.findByName("Test Product", "FLOWERS");
            assertNotNull(actual);
            assertEquals(2, actual.size());
        }

        @Test
        void findByNameOnly() {
            List<Product> actual = repository.findByName("Test Product", "");
            assertNotNull(actual);
            assertEquals(3, actual.size());
        }

        @Test
        void findByNameFailure() {
            List<Product> actual = repository.findByName("Not Found", "FLOWERS");

            assertEquals(0, actual.size());
        }
    }

    @Nested
    class findByCategory {
        @Test
        void findByCategorySuccess() {
            List<Product> actual = repository.findByCategory("FLOWERS");
            assertNotNull(actual);
            assertEquals(2, actual.size());
        }

        @Test
        void findByCategoryFailure() {
            List<Product> actual = repository.findByCategory("NOT_FOUND");

            assertEquals(0, actual.size());
        }
    }

    @Nested
    class CreateTests {
        @Test
        void happyPath() {
            Product toAdd = TestHelper.makeProduct();
            toAdd.setProductId(0);
            toAdd.setProductName("New Product");
            toAdd.setCategory(Category.EDIBLES);

            Product actual = repository.create(toAdd);

            assertEquals(TestHelper.NEXT_PRODUCT_ID, actual.getProductId());
        }

        // Need to add test for uniqueness
//        @Test
//        void throwsIfNotUnique() {
//            Product toAdd = TestHelper.makeProduct();
//            toAdd.setProductId(0);
//
//            Product actual = repository.create(toAdd);
//
//        }
    }


    @Nested
    class UpdateTests {
        @Test
        void happyPath() {
            Product toUpdate = TestHelper.makeProduct();
            toUpdate.setProductName("Updated Product");
            toUpdate.setCategory(Category.EDIBLES);

            boolean actual = repository.update(toUpdate);

            assertTrue(actual);
        }

        @Test
        void returnsFalseIfNotFound() {
            Product toUpdate = TestHelper.makeProduct();
            toUpdate.setProductId(999);
            toUpdate.setProductName("Updated Product");
            toUpdate.setCategory(Category.EDIBLES);

            boolean actual = repository.update(toUpdate);

            assertFalse(actual);
        }
    }

}