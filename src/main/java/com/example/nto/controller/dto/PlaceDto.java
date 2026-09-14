package com.example.nto.controller.dto;

import com.example.nto.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private long id;
    private String place;

    public static PlaceDto toDto(Place place) {
        return new PlaceDto(place.getId(), place.getPlace());
    }
}
