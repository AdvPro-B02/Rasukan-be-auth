package advpro.b2.rasukanauth.controller;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    private UserBuilder builder;
    private User user;

    @BeforeEach
    void setUp() {
        builder = new UserBuilder();
        builder.setId(UUID.fromString("bdc41219-e720-479e-92ca-13ccf6ea546a"));
        builder.setName("TestController");
        builder.setEmail("test@controller.com");
        builder.setPassword("controller");
        user = builder.build();
    }

    @Test
    void testGetUser_userExists() throws Exception {
        doReturn(user).when(userService).getUserById(any(String.class));
        mvc.perform(get("/api/users/bdc41219-e720-479e-92ca-13ccf6ea546a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is("true")))
                .andExpect(jsonPath("$.id", is(user.getId().toString())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.balance", is(Integer.toString(user.getBalance()))));
    }

    @Test
    void testGetUser_userDoesNotExists() throws Exception {
        doThrow(NoSuchElementException.class).when(userService).getUserById(any(String.class));
        mvc.perform(get("/api/users/bdc41219-e720-479e-92ca-13ccf6ea546a"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is("false")));
    }

    @Test
    void testGetAllUser() throws Exception {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            userList.add(user);
            builder.setId(UUID.randomUUID());
            builder.setEmail(String.format("test%02d@controller.com", i));
            user = builder.build();
        }
        doReturn(userList).when(userService).getAllUser();

        ResultActions res = mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)));
        for (int i = 0; i < userList.size(); i++) {
            User usr = userList.get(i);
            res.andExpect(jsonPath("$.["+i+"].id", is(usr.getId().toString())));
            res.andExpect(jsonPath("$.["+i+"].name", is(usr.getName())));
            res.andExpect(jsonPath("$.["+i+"].balance", is(Integer.toString(usr.getBalance()))));
        }
    }

    @Test
    void testGetUserBalance_userExists() throws Exception {
        doReturn(user.getBalance()).when(userService).getUserBalance(any(String.class));
        mvc.perform(get("/api/users/bdc41219-e720-479e-92ca-13ccf6ea546a/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is("true")))
                .andExpect(jsonPath("$.balance", is(Integer.toString(user.getBalance()))));
    }

    @Test
    void testGetUserBalance_userDoesNotExists() throws Exception {
        doThrow(NoSuchElementException.class).when(userService).getUserBalance(any(String.class));
        mvc.perform(get("/api/users/bdc41219-e720-479e-92ca-13ccf6ea546a/balance"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is("false")));
    }

    @Test
    void testUpateUserBalance_userExists() throws Exception {
        doReturn(user).when(userService).updateUserBalance(any(String.class), any(int.class));
        mvc.perform(put("/api/users/bdc41219-e720-479e-92ca-13ccf6ea546a/balance")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("balance=100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is("true")))
                .andExpect(jsonPath("$.id", is(user.getId().toString())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.balance", is(Integer.toString(user.getBalance()))));
    }

    @Test
    void testUpdateUserBalance_userDoesNotExists() throws Exception {
        doThrow(NoSuchElementException.class).when(userService).updateUserBalance(any(String.class), any(int.class));
        mvc.perform(put("/api/users/bdc41219-e720-479e-92ca-13ccf6ea546a/balance")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("balance=100"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is("false")));
    }
}
