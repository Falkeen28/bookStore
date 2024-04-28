package com.bookStore.bookStore.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class MyUserDTO {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 3, message = "Password must be at least 6 characters long")
    private String password;

    private String role;
}

