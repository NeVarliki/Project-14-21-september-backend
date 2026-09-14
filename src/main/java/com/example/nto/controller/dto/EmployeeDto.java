package com.example.nto.controller.dto;

import com.example.nto.entity.Booking;
import com.example.nto.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private String name;
    private String photoUrl;
    private Map<LocalDate, PlaceDto> booking;

    public static EmployeeDto toDto(Employee employee) {
        Map<LocalDate, PlaceDto> dtoTreeMap = new TreeMap<>();
        for (Booking booking : employee.getBookingList()) {
            dtoTreeMap.put(booking.getDate(), PlaceDto.toDto(booking.getPlace()));
        }

        return new EmployeeDto(employee.getName(), employee.getPhotoUrl(), dtoTreeMap);
    }
}
