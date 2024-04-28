package com.bookStore.bookStore.controller;

import com.bookStore.bookStore.service.MyUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyUserService myUserService;

    @Test
    @WithMockUser
    public void testRegistration() throws Exception {
        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "newUser")
                        .param("password", "password")
                        .with(csrf())) // Include CSRF token in the request
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?success"));
    }
}
