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

import java.util.Base64;
import java.util.NoSuchElementException;
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
    void testLogin_success() {
        String email = "test@test.com";
        String password = "testpass";
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.setId(UUID.randomUUID());
        userBuilder.setName("TestService");
        userBuilder.setEmail(email);
        userBuilder.setPassword(password);
        User user = userBuilder.build();
        doReturn(user).when(userService).getUserByEmailAndPassword(email, password);

        assertDoesNotThrow(() -> authService.login(email, password));
        verify(userService, times(1)).getUserByEmailAndPassword(email, password);
        User loggedInUser = authService.login(email, password);
        assertEquals(user.getId(), loggedInUser.getId());
    }

    @Test
    void testLogin_failed() {
        String email = "test@test.com";
        String password = "test123pass";
        doThrow(NoSuchElementException.class).when(userService).getUserByEmailAndPassword(email, password);

        assertThrows(NoSuchElementException.class, () -> authService.login(email, password));
    }

    @Test
    void testGenerateToken() {
        String id = UUID.randomUUID().toString();
        String authToken = authService.generateToken(id);
        Base64.Decoder decoder = Base64.getDecoder();
        String authTokenDec = new String(decoder.decode(authToken));
        String idRev = authService.generateToken(authTokenDec);

        assertEquals(id, new String(decoder.decode(idRev)));
    }

    @Test
    void testConvertTokenToId() {
        String id = UUID.randomUUID().toString();
        String token = authService.generateToken(id);
        String convertedToken = authService.tokenToId(token);

        assertEquals(id, convertedToken);
    }

    @Test
    void testValidateToken_validToken() {
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.setId(UUID.fromString("68e1011c-0021-4ae9-9d11-0deabf9cb449"));
        userBuilder.setName("TestService");
        userBuilder.setEmail("service@test.com");
        userBuilder.setPassword("servicetest");
        User user = userBuilder.build();

        String token = authService.generateToken(user.getId().toString());
        doReturn(user).when(userService).getUserById(any(String.class));
        assertTrue(authService.validateToken(token));
    }

    @Test
    void testValidateToken_invalidToken() {
        String invalidToken = authService.generateToken("2087f243-db5c-4116-a62b-40f9977b9631");
        doReturn(null).when(userService).getUserById(any(String.class));
        assertFalse(authService.validateToken(invalidToken));
    }
}
