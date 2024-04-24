package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {}

    @Test
    void testCreateUser_success() {
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.setId(UUID.fromString("68e1011c-0021-4ae9-9d11-0deabf9cb449"));
        userBuilder.setName("TestService");
        userBuilder.setEmail("service@test.com");
        userBuilder.setPassword("servicetest");
        User userExp = userBuilder.build();

        doReturn(userExp).when(userRepository).save(any(User.class));

        User user = userService.createUser(userBuilder);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(userExp.getId(), user.getId());
        assertEquals(userExp.getName(), user.getName());
        assertEquals(userExp.getEmail(), user.getEmail());
        assertEquals(userExp.getPassword(), user.getPassword());
        assertFalse(user.isStaff());
    }

    @Test
    void testGetUserById() {
        assertNull(userService.getUserById());
    }

    @Test
    void testGetAllUsers() {
        assertNull(userService.getAllUser());
    }
}
