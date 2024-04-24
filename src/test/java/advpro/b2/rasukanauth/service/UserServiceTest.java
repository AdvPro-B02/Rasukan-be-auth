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

        doReturn("68e1011c-0021-4ae9-9d11-0deabf9cb449").when(userRepository).save(userBuilder.build());

        User user = userService.createUser(userBuilder);
        verify(userRepository, times(1)).save(userBuilder.build());
        assertEquals("68e1011c-0021-4ae9-9d11-0deabf9cb449", user.getId().toString());
        assertEquals("TestService", user.getName());
        assertEquals("service@test.com", user.getEmail());
        assertEquals("servicetest", user.getPassword());
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
