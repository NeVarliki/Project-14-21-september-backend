package com.example.nto.service.impl;

import com.example.nto.controller.dto.BookingCreateDto;
import com.example.nto.controller.dto.PlaceDto;
import com.example.nto.entity.Booking;
import com.example.nto.entity.Place;
import com.example.nto.entity.User;
import com.example.nto.exception.BookingAlreadyExistsException;
import com.example.nto.exception.PlaceNotFoundException;
import com.example.nto.exception.UserNotFoundException;
import com.example.nto.repository.BookingRepository;
import com.example.nto.repository.PlaceRepository;
import com.example.nto.repository.UserRepository;
import com.example.nto.service.BookingService;
import com.example.nto.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PlaceRepository placeRepository;
    private final EmployeeService employeeService;

    private final int daysAhead = 3;

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<LocalDate, List<PlaceDto>> getFreePlace() {

        List<Place> allPlaces = placeRepository.findAll();

        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate end = today.plusDays(daysAhead);

        List<Booking> bookings = bookingRepository.findByDateBetween(today, end);

        Map<LocalDate, Set<Long>> busyByDate = bookings.stream()
                .collect(Collectors.groupingBy(
                        Booking::getDate,
                        Collectors.mapping(b -> b.getPlace().getId(), Collectors.toSet())
                ));

        Map<LocalDate, List<PlaceDto>> result = new LinkedHashMap<>();

        for (int i = 0; i <= daysAhead; i++) {
            LocalDate currentDate = today.plusDays(i);
            Set<Long> busyPlaces = busyByDate.getOrDefault(currentDate, Collections.emptySet());

            List<PlaceDto> freePlaces = allPlaces.stream()
                    .filter(place -> !busyPlaces.contains(place.getId()))
                    .map(place -> new PlaceDto(place.getId(), place.getPlace()))
                    .toList();

            result.put(currentDate, freePlaces);
        }

        return result;
    }

    @Override
    @Transactional
    public Booking create(BookingCreateDto bookingCreateDto, User user) {
        LocalDate date = bookingCreateDto.getDate();
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        if (date.isBefore(today) || date.isAfter(today.plusDays(daysAhead))) {
            throw new IllegalArgumentException("Date is out of booking window");
        }

        long placeId = bookingCreateDto.getPlaceId();
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException("Place with " + placeId + " id not found!"));

        if (bookingRepository.findByDateAndPlace(date, place).isPresent()) {
            throw new BookingAlreadyExistsException("Booking already exists");
        }

        if (bookingRepository.findByDateAndUser(date, user).isPresent()) {
            throw new BookingAlreadyExistsException("Already has another booking on " + date);
        }

        Booking booking = Booking.builder()
                .date(date)
                .user(user)
                .place(place)
                .build();

        return bookingRepository.save(booking);
    }

    @Override
    public Booking createForToday(long placeId, User user) {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException("Place with " + placeId + " id not found!"));
        if (bookingRepository.findByDateAndPlace(today, place).isPresent()) {
            throw new BookingAlreadyExistsException("Booking already exists");
        }
        if (bookingRepository.findByDateAndUser(today, user).isPresent()) {
            throw new BookingAlreadyExistsException("Already has another booking on " + today);
        }

        Booking booking = Booking.builder()
                .date(today)
                .user(user)
                .place(place)
                .build();
        return bookingRepository.save(booking);
    }

    @Override
    public void free(User user) {
        bookingRepository.deleteByUser(user);
    }
}
