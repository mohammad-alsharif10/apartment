package com.rent.apartment.mapper;


import com.rent.apartment.dto.ApartmentDto;
import com.rent.apartment.model.Apartment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {UserMapper.class, ImageMapper.class})
public interface ApartmentMapper extends BaseMapper<Long, ApartmentDto, Apartment> {

    @Override
    ApartmentDto toBaseDto(Apartment baseModel);

    @Override
    Apartment toBaseEntity(ApartmentDto baseDto);

    @Override
    List<ApartmentDto> toBaseDtoList(List<Apartment> list);

    @Override
    List<Apartment> toBaseEntityList(List<ApartmentDto> list);
}
