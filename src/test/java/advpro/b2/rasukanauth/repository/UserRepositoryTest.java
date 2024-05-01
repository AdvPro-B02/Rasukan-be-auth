package advpro.b2.rasukanauth.repository;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class UserRepositoryTest {

    @MockBean
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
        doReturn(user).when(userRepository).findByEmailAndPassword(email, password);

        User searchUser = userRepository.findByEmailAndPassword(email, password);
        assertNotNull(searchUser);
        assertEquals(user.getId(), searchUser.getId());
        assertEquals(user.getEmail(), searchUser.getEmail());
    }

    @Test
    void testFindUserByEmailAndPassword_fail() {
        String email = "test1@test.com";
        String password = "test1";
        doReturn(null).when(userRepository).findByEmailAndPassword(email, password);

        User user = userRepository.findByEmailAndPassword(email, password);
        assertNull(user);
    }
}
