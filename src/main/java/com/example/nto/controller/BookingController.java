package com.example.nto.controller;

import com.example.nto.controller.dto.BookingCreateDto;
import com.example.nto.controller.dto.PlaceDto;
import com.example.nto.entity.User;
import com.example.nto.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/booking")
    public Map<LocalDate, List<PlaceDto>> getByDate() {
        return bookingService.getFreePlace();
    }

    @PostMapping("/book")
    public void create(@RequestBody @Valid BookingCreateDto bookingCreateDto, @AuthenticationPrincipal User user) {
        bookingService.create(bookingCreateDto, user);
    }

}
