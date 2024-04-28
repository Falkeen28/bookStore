package com.bookStore.bookStore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBookListDTO {
    private int id;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters long")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters long")
    private String author;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be less than 0")
    private int quantity;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be at least 1")
    private int price;
}