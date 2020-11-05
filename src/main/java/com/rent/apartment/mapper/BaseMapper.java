package com.rent.apartment.mapper;


import com.rent.apartment.dto.BaseDto;
import com.rent.apartment.model.BaseModel;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<ID extends Serializable, baseDto extends BaseDto<ID>, baseModel extends BaseModel<ID>> {

    baseDto toBaseDto(baseModel baseModelPram);

    baseModel toBaseEntity(baseDto baseDtoPram);

    List<baseDto> toBaseDtoList(List<baseModel> baseModelList);

    List<baseModel> toBaseEntityList(List<baseDto> baseDtoList);

}
