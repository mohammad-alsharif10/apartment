package com.rent.apartment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto extends BaseDto<Long> {

    private String path;

    private Boolean isCurrentProfileImage;
}
