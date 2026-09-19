package com.example.nto.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshDTO(
        @NotBlank
        String refresh
) {
}
