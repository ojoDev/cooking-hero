package com.ojodev.cookinghero.recipes.business;

import com.ojodev.cookinghero.recipes.domain.exception.ApiBadRequestException;
import com.ojodev.cookinghero.recipes.domain.exception.ApiException;
import com.ojodev.cookinghero.recipes.domain.exception.NotFoundException;
import com.ojodev.cookinghero.recipes.domain.model.CuisineTypeBO;
import com.ojodev.cookinghero.recipes.domain.model.CuisineTypeMultiLanguageBO;
import com.ojodev.cookinghero.recipes.domain.model.LanguageEnumBO;
import com.ojodev.cookinghero.recipes.domain.model.MeasureBO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface MeasuresBusiness {

    List<MeasureBO> getMeasures(LanguageEnumBO language);

    Optional<MeasureBO> getMeasure(String measureId, LanguageEnumBO language);

  /*  void addMeasure(MeasureMultiLanguageBO newMeasure) throws ApiBadRequestException;

    void addOrReplaceMeasure(MeasureBO cuisineType) throws ApiException;

    void deleteMeasure(String measureId) throws NotFoundException;*/
}
