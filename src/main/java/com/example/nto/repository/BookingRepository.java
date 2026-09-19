package com.example.nto.repository;

import com.example.nto.entity.Booking;
import com.example.nto.entity.Place;
import com.example.nto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByDateBetween(LocalDate start, LocalDate end);

    Optional<Booking> findByDateAndPlace(LocalDate date, Place place);

    Optional<Booking> findByDateAndUser(LocalDate date, User user);
    List<Booking> findAllByUser(User user);
}
