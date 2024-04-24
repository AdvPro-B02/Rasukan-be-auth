package advpro.b2.rasukanauth.controller;

import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.service.AuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(value = AuthController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private AuthService authService;

    @BeforeAll
    static void setUp() {}

    @Test
    void testRegister_success() throws Exception {
        doReturn("02619f07-c3e1-408e-bc1c-028e20cfe79e").when(authService).register(any(UserBuilder.class));
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("id=02619f07-c3e1-408e-bc1c-028e20cfe79e&name=Test1&email=test1@test.com&password=test1")
                        .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "02619f07-c3e1-408e-bc1c-028e20cfe79e"
            ));
    }

    @Test
    void testLogin_success() throws Exception {
        mvc.perform(post("/auth/login").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hi, this API currently is not functional. Thanks for the interest"
            ));
    }

    @Test
    void testLogout_success() throws Exception {
        mvc.perform(post("/auth/logout").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hi, this API currently is not functional. Thanks for the interest"
            ));
    }

    @Test
    void testGetTokenLoggedIn() throws Exception {
        mvc.perform(post("/auth/get-token").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hi, this API currently is not functional. Thanks for the interest"
            ));
    }

    @Test
    void testGetTokenNotLoggedIn() throws Exception {
        mvc.perform(post("/auth/get-token").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hi, this API currently is not functional. Thanks for the interest"
            ));
    }
}
