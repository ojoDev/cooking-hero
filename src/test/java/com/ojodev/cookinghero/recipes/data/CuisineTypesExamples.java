package com.ojodev.cookinghero.recipes.data;

import com.ojodev.cookinghero.recipes.api.model.CuisineType;
import com.ojodev.cookinghero.recipes.api.model.CuisineTypeNew;
import com.ojodev.cookinghero.recipes.api.model.CuisineTypeNewName;
import com.ojodev.cookinghero.recipes.api.model.LanguageEnum;
import com.ojodev.cookinghero.recipes.domain.model.CuisineTypeBO;
import com.ojodev.cookinghero.recipes.infrastructure.po.CuisineTypePO;
import com.ojodev.cookinghero.recipes.infrastructure.po.LanguageNamePO;
import org.springframework.data.mongodb.core.mapping.Language;

import java.util.Arrays;

public class CuisineTypesExamples {

    public static final String CUISINE_TYPE_01_ID = "1";
    public static final String CUISINE_TYPE_01_NAME_SPANISH = "italiana";
    public static final String CUISINE_TYPE_01_NAME_ENGLISH = "italian";
    public static final CuisineTypeBO CUISINE_TYPE_BO_01_ENGLISH = new CuisineTypeBO(CUISINE_TYPE_01_ID, CUISINE_TYPE_01_NAME_ENGLISH);
    public static final CuisineTypeBO CUISINE_TYPE_BO_01_SPANISH = new CuisineTypeBO(CUISINE_TYPE_01_ID, CUISINE_TYPE_01_NAME_SPANISH);

    public static final String CUISINE_TYPE_02_ID = "AJFFG00043";
    public static final String CUISINE_TYPE_02_NAME_ENGLISH = "japanese";
    public static final String CUISINE_TYPE_02_NAME_SPANISH = "japonesa";
    public static final CuisineTypeBO CUISINE_TYPE_BO_02_ENGLISH = new CuisineTypeBO(CUISINE_TYPE_02_ID, CUISINE_TYPE_02_NAME_ENGLISH);
    public static final CuisineTypeBO CUISINE_TYPE_BO_02_SPANISH = new CuisineTypeBO(CUISINE_TYPE_02_ID, CUISINE_TYPE_02_NAME_SPANISH);

    public static final String CUISINE_TYPE_03_ID = "0055";
    public static final String CUISINE_TYPE_03_NAME_ENGLISH = "veggie";
    public static final String CUISINE_TYPE_03_NAME_SPANISH = "vegatariana";
    public static final CuisineTypeBO CUISINE_TYPE_BO_03_ENGLISH = new CuisineTypeBO(CUISINE_TYPE_03_ID, CUISINE_TYPE_03_NAME_ENGLISH);
    public static final CuisineTypeBO CUISINE_TYPE_BO_03_SPANISH = new CuisineTypeBO(CUISINE_TYPE_03_ID, CUISINE_TYPE_03_NAME_SPANISH);

    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_ES = "es";
    public static final CuisineTypePO CUISINE_TYPE_PO_01 = new CuisineTypePO(CUISINE_TYPE_01_ID, Arrays.asList(
            new LanguageNamePO(1l, LANGUAGE_EN, CUISINE_TYPE_01_NAME_ENGLISH),
            new LanguageNamePO(2l, LANGUAGE_ES, CUISINE_TYPE_01_NAME_SPANISH)));
    public static final CuisineTypePO CUISINE_TYPE_PO_02 = new CuisineTypePO(CUISINE_TYPE_02_ID, Arrays.asList(
            new LanguageNamePO(3l, LANGUAGE_EN, CUISINE_TYPE_02_NAME_ENGLISH),
            new LanguageNamePO(4l, LANGUAGE_ES, CUISINE_TYPE_02_NAME_SPANISH)));
    public static final CuisineTypePO CUISINE_TYPE_PO_03 = new CuisineTypePO(CUISINE_TYPE_03_ID, Arrays.asList(
            new LanguageNamePO(5l, LANGUAGE_EN, CUISINE_TYPE_03_NAME_ENGLISH),
            new LanguageNamePO(6l, LANGUAGE_ES, CUISINE_TYPE_03_NAME_SPANISH)));

    public static final CuisineTypeNew CUISINE_TYPE_NEW = initCuisineTypeNew() ;

    public static final CuisineTypeNew CUISINE_TYPE_NEW_NO_DEFALT_LANGUAGE = initCuisineTypeNewNoDefaultLanguage() ;

    private static CuisineTypeNew initCuisineTypeNew() {
        return new  CuisineTypeNew(Arrays.asList(
                new CuisineTypeNewName(CUISINE_TYPE_01_NAME_ENGLISH, LanguageEnum.EN),
                new CuisineTypeNewName(CUISINE_TYPE_01_NAME_SPANISH, LanguageEnum.ES)));
    }

    private static CuisineTypeNew initCuisineTypeNewNoDefaultLanguage() {
        return new  CuisineTypeNew(Arrays.asList(
                new CuisineTypeNewName(CUISINE_TYPE_01_NAME_SPANISH, LanguageEnum.ES)));
    }
}
