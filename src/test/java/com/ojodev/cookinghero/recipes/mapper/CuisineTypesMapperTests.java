package com.ojodev.cookinghero.recipes.mapper;

import com.ojodev.cookinghero.recipes.api.model.CuisineType;
import com.ojodev.cookinghero.recipes.api.model.LanguageEnum;
import com.ojodev.cookinghero.recipes.data.CuisineTypesExamples;
import com.ojodev.cookinghero.recipes.domain.model.CuisineTypeBO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CuisineTypesMapperTests {

    @Autowired
    private CuisineTypesMapper cuisineTypeMapper;


    @Test
    public void contertCuisineTypeBOToCuisineType() {
        CuisineType cuisineType = cuisineTypeMapper.toCuisineType(CuisineTypesExamples.CUISINE_TYPE_BO_01_ENGLISH);
        assertNotNull(cuisineType);
        assertEquals(CuisineTypesExamples.CUISINE_TYPE_01_ID,cuisineType.getId());
        assertEquals(CuisineTypesExamples.CUISINE_TYPE_01_NAME_ENGLISH,cuisineType.getName());
    }


    @Test
    public void contertCuisineTypeBOListToCuisineTypeList() {
        List<CuisineType> cuisineTypes = cuisineTypeMapper.toCuisineTypeList(Arrays.asList(CuisineTypesExamples.CUISINE_TYPE_BO_01_ENGLISH,CuisineTypesExamples.CUISINE_TYPE_BO_02_ENGLISH));
        assertNotNull(cuisineTypes);
        assertEquals(2, cuisineTypes.size());
        assertEquals(CuisineTypesExamples.CUISINE_TYPE_01_ID,cuisineTypes.get(0).getId());
        assertEquals(CuisineTypesExamples.CUISINE_TYPE_01_NAME_ENGLISH,cuisineTypes.get(0).getName());
        assertEquals(CuisineTypesExamples.CUISINE_TYPE_02_ID,cuisineTypes.get(1).getId());
        assertEquals(CuisineTypesExamples.CUISINE_TYPE_02_NAME_ENGLISH,cuisineTypes.get(1).getName());
    }

    @Test
    public void convertCuisineTypePOToCuisineTypeBO() {
        CuisineTypeBO cuisineTypeEnBO = cuisineTypeMapper.toCuisineTypeBO(CuisineTypesExamples.CUISINE_TYPE_PO_01, LanguageEnum.EN);
        assertNotNull(cuisineTypeEnBO);
        assertEquals(CuisineTypesExamples.CUISINE_TYPE_01_ID,cuisineTypeEnBO.getId());
        assertEquals(CuisineTypesExamples.CUISINE_TYPE_01_NAME_ENGLISH,cuisineTypeEnBO.getName());

        CuisineTypeBO cuisineTypeEsBO = cuisineTypeMapper.toCuisineTypeBO(CuisineTypesExamples.CUISINE_TYPE_PO_01, LanguageEnum.ES);
        assertNotNull(cuisineTypeEsBO);
        assertEquals(CuisineTypesExamples.CUISINE_TYPE_01_ID,cuisineTypeEsBO.getId());
        assertEquals(CuisineTypesExamples.CUISINE_TYPE_01_NAME_SPANISH,cuisineTypeEsBO.getName());
    }

}
