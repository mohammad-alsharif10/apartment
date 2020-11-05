package com.rent.apartment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto<Long> {

    private String username;

    @JsonIgnore
    private String password;

    private Instant created;

    private boolean enabled;

    private String firstName;

    private String lastName;

    private String faculty;

    private String gender;

    private String university;

    private List<ImageDto> imageDtoList = new ArrayList<>();

    private List<ApartmentDto> apartmentDtoList = new ArrayList<>();
}
