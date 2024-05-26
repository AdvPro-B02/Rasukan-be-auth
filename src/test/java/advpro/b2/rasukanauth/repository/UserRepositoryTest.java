package advpro.b2.rasukanauth.repository;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private UserBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new UserBuilder();
        builder.setId(UUID.randomUUID());
        builder.setName("Test1");
        builder.setEmail("test1@test.com");
        builder.setPassword("test1");
        builder.setPhoneNumber("123456");
        builder.setBalance(123456);
    }

    @Test
    void testFindUserByEmailAndPassword_success() {
        User user = builder.build();
        String email = "test1@test.com";
        String password = "test1";
        user = userRepository.save(user);

        User searchUser = userRepository.findByEmailAndPassword(email, password);
        assertNotNull(searchUser);
        assertEquals(user.getId(), searchUser.getId());
        assertEquals(user.getEmail(), searchUser.getEmail());
    }

    @Test
    void testFindUserByEmailAndPassword_fail() {
        User user = builder.build();
        String email = "test2@test.com";
        String password = "test1";
        userRepository.save(user);

        User searchedUser = userRepository.findByEmailAndPassword(email, password);
        assertNull(searchedUser);
    }
}
