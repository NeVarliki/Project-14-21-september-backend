package com.example.nto.controller.dto;

import com.example.nto.validation.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Password
public record AuthRequestDTO(
        @NotBlank
        @Pattern(regexp = "^[.a-zA-Z0-9]+$", message = "AJDJSJDSJSDJDS")
        String username,
        @Size(min = 8)
        String password) {
}
