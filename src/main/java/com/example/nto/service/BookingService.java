package com.example.nto.service;

import com.example.nto.controller.dto.BookingCreateDto;
import com.example.nto.controller.dto.PlaceDto;
import com.example.nto.entity.Booking;
import com.example.nto.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingService {
    Map<LocalDate, List<PlaceDto>> getFreePlace();

    Booking create(BookingCreateDto bookingCreateDto, User user);
}
