package com.example.nto.controller.dto;

import com.example.nto.entity.Booking;
import com.example.nto.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String photoUrl;
    private Map<LocalDate, PlaceDto> booking;

    public static UserDTO toDto(User user, List<Booking> bookings) {
        Map<LocalDate, PlaceDto> dtoTreeMap = new TreeMap<>();
        if(bookings != null) {
            for (Booking booking : bookings) {
                dtoTreeMap.put(booking.getDate(), PlaceDto.toDto(booking.getPlace()));
            }
        }

        return new UserDTO(user.getName(), user.getPhotoUrl(), dtoTreeMap);
    }
}
