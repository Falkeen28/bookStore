package com.bookStore.bookStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    @Column(name = "QUANTITY")
    private int quantity;
    private int price;
    public void decrementQuantity() {
        if (this.quantity > 0) {
            this.quantity -= 1;
        } else {
            throw new IllegalStateException("Cannot decrement quantity below 0.");
        }
    }
    public void incrementQuantity() {
        this.quantity += 1;
    }
}