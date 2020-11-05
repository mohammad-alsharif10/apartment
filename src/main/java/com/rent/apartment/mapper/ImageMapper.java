package com.rent.apartment.mapper;


import com.rent.apartment.dto.ImageDto;
import com.rent.apartment.model.Image;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper extends BaseMapper<Long, ImageDto, Image> {

    @Override
    ImageDto toBaseDto(Image baseModel);

    @Override
    Image toBaseEntity(ImageDto baseDto);

    @Override
    List<ImageDto> toBaseDtoList(List<Image> list);

    @Override
    List<Image> toBaseEntityList(List<ImageDto> list);
}
