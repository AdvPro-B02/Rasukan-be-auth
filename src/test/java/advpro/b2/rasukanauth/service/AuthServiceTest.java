package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;
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
public class AuthServiceTest {

    @TestConfiguration
    static class AuthServiceImplTestContextConfiguration {

        @Bean
        public AuthService authService(UserService userService) {
            return new AuthServiceImpl(userService);
        }

    }

    @Autowired
    private AuthService authService;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {}

    @Test
    void testRegister_success() {
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.setId(UUID.fromString("68e1011c-0021-4ae9-9d11-0deabf9cb449"));
        userBuilder.setName("TestService");
        userBuilder.setEmail("service@test.com");
        userBuilder.setPassword("servicetest");
        User user = userBuilder.build();

        doReturn(user).when(userService).createUser(userBuilder);
        String registeredUserId = authService.register(userBuilder);
        verify(userService, times(1)).createUser(userBuilder);
        assertEquals(user.getId().toString(), registeredUserId);
    }

    @Test
    void testLogin() {
        assertNull(authService.login());
    }

    @Test
    void testLogout() {
        assertNull(authService.logout());
    }

    @Test
    void testGenerateToken() {
        assertNull(authService.generateToken());
    }

    @Test
    void testValidateToken() {
        assertFalse(authService.validateToken());
    }
}
