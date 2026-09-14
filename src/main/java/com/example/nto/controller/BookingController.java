package com.example.nto.controller;

import com.example.nto.controller.dto.BookingCreateDto;
import com.example.nto.controller.dto.PlaceDto;
import com.example.nto.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{code}/booking")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<LocalDate, List<PlaceDto>> getByDate(@PathVariable String code) {
        return bookingService.getFreePlace(code);
    }

    @PostMapping("/{code}/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@PathVariable String code, @RequestBody BookingCreateDto bookingCreateDto) {
        bookingService.create(code, bookingCreateDto);
    }

}
