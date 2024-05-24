package advpro.b2.rasukanauth.controller;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.service.AuthService;
import advpro.b2.rasukanauth.service.UserService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AuthController.class)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private AuthService authService;

    @BeforeAll
    static void setUp() {}

    @Test
    void testRegister_success() throws Exception {
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("id=02619f07-c3e1-408e-bc1c-028e20cfe79e&name=Test1&email=test1@test.com&password=test1"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status", is("success")));
    }

    @Test
    void testRegisterNonUniqueEmail_fail() throws Exception {
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("id=02619f07-c3e1-408e-bc1c-028e20cfe79e&name=Test1&email=test1@test.com&password=test1"))
            .andExpect(status().isCreated());

        doThrow(RuntimeException.class).when(authService).register(any(UserBuilder.class));
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("id=b9eed58d-031c-466f-bdb8-2cf95a9d11e1&name=Test2&email=test1@test.com&password=test2"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", not(is("success"))));
    }

    @Test
    void testLogin_success() throws Exception {
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("id=02619f07-c3e1-408e-bc1c-028e20cfe79e&name=Test1&email=test1@test.com&password=test1"));

        UserBuilder builder = new UserBuilder();
        builder.setId(UUID.fromString("68e1011c-0021-4ae9-9d11-0deabf9cb449"));
        User user = builder.build();
        doReturn(user).when(authService).login(any(String.class), any(String.class));
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("email=test1@test.com&password=test1"))
            .andExpect(status().isOk())
            .andExpect(header().exists("Set-Cookie"))
            .andExpect(cookie().exists("token"))
            .andExpect(cookie().exists("staff"));
    }

    @Test
    void testLoginIncorrectEmail_fail() throws Exception {
        mvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("id=02619f07-c3e1-408e-bc1c-028e20cfe79e&name=Test1&email=test1@test.com&password=test1"));

        doThrow(NoSuchElementException.class).when(authService).login(any(String.class), any(String.class));
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("email=test2@test.com&password=test1"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist("Set-Cookie"))
                .andExpect(cookie().doesNotExist("token"))
                .andExpect(cookie().doesNotExist("staff"));
    }

    @Test
    void testLoginIncorrectPassword_fail() throws Exception {
        mvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("id=02619f07-c3e1-408e-bc1c-028e20cfe79e&name=Test1&email=test1@test.com&password=test1"));

        doThrow(NoSuchElementException.class).when(authService).login(any(String.class), any(String.class));
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("email=test1@test.com&password=test2"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist("Set-Cookie"))
                .andExpect(cookie().doesNotExist("token"))
                .andExpect(cookie().doesNotExist("staff"));
    }

    @Test
    void testLoginIncorrectEmailAndPassword_fail() throws Exception {
        mvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("id=02619f07-c3e1-408e-bc1c-028e20cfe79e&name=Test1&email=test1@test.com&password=test1"));

        doThrow(NoSuchElementException.class).when(authService).login(any(String.class), any(String.class));
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("email=test2@test.com&password=test2"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist("Set-Cookie"))
                .andExpect(cookie().doesNotExist("token"))
                .andExpect(cookie().doesNotExist("staff"));
    }

    @Test
    void testValidateToken() throws Exception {
        doReturn(true).when(authService).validateToken(any(String.class));
        mvc.perform(post("/auth/validate")
                .cookie(new Cookie("token", "L0k5OUgJfBR3aE4JfQBuOkBeYBRobhgWfB45bh8NKExvOU9a")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.valid", is(true)));

        doReturn(false).when(authService).validateToken(any(String.class));
        mvc.perform(post("/auth/validate")
                .cookie(new Cookie("token", "L0k5OUgJfBR3aE4JfQBuOkBeYBRobhgWfB45bh8NKExvOU9a")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.valid", is(false)));
    }
}
