package com.ojodev.cookinghero.recipes.mapper;

import com.ojodev.cookinghero.recipes.api.model.Step;
import com.ojodev.cookinghero.recipes.domain.model.StepBO;
import com.ojodev.cookinghero.recipes.infrastructure.po.StepPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StepsMapper {

    Step toStep(StepBO stepBO);

    @Mapping(target = "id", source = "objectId")
    @Mapping(target = "media.id", source = "media.objectId")
    @Mapping(target = "media.mediaType", source = "media.mediaType")
    StepBO toStepBO(StepPO stepPO);

}
