package com.bookStore.bookStore.controller;

import com.bookStore.bookStore.service.BookService;
import com.bookStore.bookStore.service.MyBooksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private MyBooksService myBooksService; // Mock the MyBooksService as well

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooksSorted(anyString(), anyString())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/available_books").with(user("user").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("bookList"))
                .andExpect(model().attributeExists("books"));
    }

}
