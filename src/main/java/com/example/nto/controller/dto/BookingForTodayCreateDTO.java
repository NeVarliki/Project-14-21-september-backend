package com.example.nto.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookingForTodayCreateDTO(
        @Positive
        @NotNull
        long id
) {
}
