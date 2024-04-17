package advpro.b2.rasukanauth.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @BeforeAll
    static void setUp() {}

    @Test
    void testRegister_success() throws Exception {
        mvc.perform(post("/auth/register"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hi, this API currently is not functional. Thanks for the interest"
            ));
    }

    @Test
    void testLogin_success() throws Exception {
        mvc.perform(post("/auth/login"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hi, this API currently is not functional. Thanks for the interest"
            ));
    }

    @Test
    void testLogout_success() throws Exception {
        mvc.perform(post("/auth/logout"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hi, this API currently is not functional. Thanks for the interest"
            ));
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
