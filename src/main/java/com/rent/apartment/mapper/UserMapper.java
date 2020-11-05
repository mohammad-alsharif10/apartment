package com.rent.apartment.mapper;



import com.rent.apartment.dto.UserDto;
import com.rent.apartment.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;


@Mapper(componentModel = "spring", uses = {ApartmentMapper.class, ImageMapper.class})
public interface UserMapper extends BaseMapper<Long, UserDto, User> {


    @Override
    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "apartmentDtoList", ignore = true)
    })
    UserDto toBaseDto(User baseModel);

    @Override
    User toBaseEntity(UserDto baseDto);

    @Override
    List<UserDto> toBaseDtoList(List<User> list);

    @Override
    List<User> toBaseEntityList(List<UserDto> list);
}
