package advpro.b2.rasukanauth.controller;

import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.service.AuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
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
            .andExpect(status().isCreated());
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
            .andExpect(status().isBadRequest());
    }

    @Test
    void testLogin_success() throws Exception {
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("id=02619f07-c3e1-408e-bc1c-028e20cfe79e&name=Test1&email=test1@test.com&password=test1"));

        doReturn("dummy-token").when(authService).login(any(String.class), any(String.class));
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("email=test1@test.com&password=test1"))
            .andExpect(status().isOk())
            .andExpect(header().exists("Authorization"));
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
                .andExpect(header().doesNotExist("Authorization"));
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
                .andExpect(header().doesNotExist("Authorization"));
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
                .andExpect(header().doesNotExist("Authorization"));
    }

    @Test
    void testGetTokenLoggedIn() throws Exception {
        mvc.perform(post("/auth/get-token"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hi, this API currently is not functional. Thanks for the interest"
            ));
    }

    @Test
    void testGetTokenNotLoggedIn() throws Exception {
        mvc.perform(post("/auth/get-token"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hi, this API currently is not functional. Thanks for the interest"
            ));
    }
}
