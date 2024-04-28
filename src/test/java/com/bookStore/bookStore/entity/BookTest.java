package com.bookStore.bookStore.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void decrementQuantitySuccess() {
        Book book = new Book(1, "Java Basics", "Author Name", 10, 20);
        book.decrementQuantity();
        assertEquals(9, book.getQuantity(), "Quantity should decrement by 1");
    }

    @Test
    void decrementQuantityFail() {
        Book book = new Book(1, "Java Basics", "Author Name", 0, 20);
        assertThrows(IllegalStateException.class, book::decrementQuantity, "Should throw exception when quantity is 0");
    }

    @Test
    void incrementQuantitySuccess() {
        Book book = new Book(1, "Java Basics", "Author Name", 5, 20);
        book.incrementQuantity();
        assertEquals(6, book.getQuantity(), "Quantity should increment by 1");
    }
}
