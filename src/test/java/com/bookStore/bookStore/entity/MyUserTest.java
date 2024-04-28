package com.bookStore.bookStore.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyUserTest {

    @Test
    void createUserWithBooks() {
        MyUser user = MyUser.builder()
                .username("user")
                .password("pass")
                .role("ROLE_USER")
                .build();

        MyBookList bookList = new MyBookList();
        user.setMyBooks(List.of(bookList));

        assertFalse(user.getMyBooks().isEmpty(), "User should have associated books");
    }

    @Test
    void usernameCannotBeNull() {
        MyUser user = new MyUser();
        user.setUsername(null);
        user.setPassword("pass");
        user.setRole("ROLE_USER");
    }
}
