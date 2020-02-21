package com.ojodev.cookinghero.recipes.api.controller;

import com.google.common.net.HttpHeaders;
import com.ojodev.cookinghero.recipes.api.model.DescriptiveName;
import com.ojodev.cookinghero.recipes.api.model.DescriptiveNameUpdate;
import com.ojodev.cookinghero.recipes.api.model.ProductStatusEnum;
import com.ojodev.cookinghero.recipes.api.model.ProductUpdate;
import com.ojodev.cookinghero.recipes.business.MeasuresBusiness;
import com.ojodev.cookinghero.recipes.business.ProductsBusiness;
import com.ojodev.cookinghero.recipes.config.Messages;
import com.ojodev.cookinghero.recipes.data.ProductsExamples;
import com.ojodev.cookinghero.recipes.domain.exception.ApiFieldsException;
import com.ojodev.cookinghero.recipes.domain.exception.FieldError;
import com.ojodev.cookinghero.recipes.domain.model.DescriptiveNameBO;
import com.ojodev.cookinghero.recipes.domain.model.LanguageEnumBO;
import com.ojodev.cookinghero.recipes.domain.model.ProductBO;
import com.ojodev.cookinghero.recipes.utils.TestUtils;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductsApiControllerPatchTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Messages messages;

    @MockBean
    private ProductsBusiness productsBusiness;


    @Test
    public void patchMeasureNoDefaultLanguageComplete() throws Exception {

        ProductBO productBOEs = new ProductBO(ProductsExamples.PRODUCT_02_ID, new DescriptiveNameBO(ProductsExamples.PRODUCT_01_NAME_SPANISH_SINGULAR, ProductsExamples.PRODUCT_01_NAME_SPANISH_PLURAL, LanguageEnumBO.EN));
        ProductUpdate productUpdateComplete = new ProductUpdate(new DescriptiveNameUpdate(ProductsExamples.PRODUCT_01_NAME_ENGLISH_SINGULAR_CHANGED, ProductsExamples.PRODUCT_01_NAME_ENGLISH_PLURAL_CHANGED), ProductStatusEnum.APPROVED_BY_ADMIN);

        when(this.productsBusiness.getProduct(any(), any())).thenReturn(Optional.of(productBOEs));

        this.mvc.perform(patch("/products/{product-id}", ProductsExamples.PRODUCT_01_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, ProductsExamples.LANGUAGE_ES)
                .content(TestUtils.asJsonString(productUpdateComplete)))
                .andExpect(status().isNoContent());
    }

    @Test
    @Disabled
    public void patchMeasureNoDefaultLanguagePartial() throws Exception {

        ProductBO productBOEs = new ProductBO(ProductsExamples.PRODUCT_01_ID, new DescriptiveNameBO(ProductsExamples.PRODUCT_01_NAME_SPANISH_SINGULAR, ProductsExamples.PRODUCT_01_NAME_SPANISH_PLURAL, LanguageEnumBO.ES));
        ProductUpdate ProductUpdate = initPartialProductUpdate();

        when(this.productsBusiness.getProduct(any(), any())).thenReturn(Optional.of(productBOEs));

        this.mvc.perform(patch("/products/{product-id}", ProductsExamples.PRODUCT_01_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, ProductsExamples.LANGUAGE_ES)
                .content(TestUtils.asJsonString(ProductUpdate)))
                .andExpect(status().isNoContent());
    }


    @Test
    @Disabled
    public void patchMeasureDefaultLanguage() throws Exception {

        ProductBO productBOEn = new ProductBO(ProductsExamples.PRODUCT_01_ID, new DescriptiveNameBO(ProductsExamples.PRODUCT_01_NAME_ENGLISH_SINGULAR, ProductsExamples.PRODUCT_01_NAME_ENGLISH_PLURAL, LanguageEnumBO.EN));
        ProductUpdate ProductUpdateComplete = new ProductUpdate(new DescriptiveNameUpdate(ProductsExamples.PRODUCT_01_NAME_ENGLISH_SINGULAR_CHANGED, ProductsExamples.PRODUCT_01_NAME_ENGLISH_PLURAL_CHANGED), ProductStatusEnum.APPROVED_BY_ADMIN);

        when(this.productsBusiness.getProduct(any(), any())).thenReturn(Optional.of(productBOEn));

        ApiFieldsException exception = new ApiFieldsException(
                messages.get("error.badrequest.invalidparams.code"),
                messages.get("error.badrequest.invalidparams.desc"),
                Arrays.asList(new FieldError(messages.get("error.badrequest.invalidparams.fields.headerparaminvalid.code"),
                        HttpHeaders.ACCEPT_LANGUAGE,
                        messages.get("error.badrequest.invalidparams.fields.headerparaminvalid.desc.nodefaultlanguage")))
        );

        doThrow(exception).when(productsBusiness).addOrReplaceProduct(any());

        this.mvc.perform(patch("/products/{product-id}", ProductsExamples.PRODUCT_01_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, ProductsExamples.LANGUAGE_ES)
                .content(TestUtils.asJsonString(ProductUpdateComplete)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(messages.get("error.badrequest.invalidparams.code"))))
                .andExpect(jsonPath("$.description", is(messages.get("error.badrequest.invalidparams.desc"))))
                .andExpect(jsonPath("$.fields[0].code", is(messages.get("error.badrequest.invalidparams.fields.headerparaminvalid.code"))))
                .andExpect(jsonPath("$.fields[0].field", is(HttpHeaders.ACCEPT_LANGUAGE)))
                .andExpect(jsonPath("$.fields[0].description", is(messages.get("error.badrequest.invalidparams.fields.headerparaminvalid.desc.nodefaultlanguage"))));
    }


    @Test
    @Disabled
    public void patchMeasureNotFound() throws Exception {

        ProductUpdate ProductUpdateComplete = new ProductUpdate(new DescriptiveNameUpdate(ProductsExamples.PRODUCT_01_NAME_ENGLISH_SINGULAR_CHANGED, ProductsExamples.PRODUCT_01_NAME_ENGLISH_PLURAL_CHANGED), ProductStatusEnum.APPROVED_BY_ADMIN);

        when(this.productsBusiness.getProduct(any(), any())).thenReturn(Optional.empty());

        this.mvc.perform(patch("/products/{product-id}", ProductsExamples.PRODUCT_01_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, ProductsExamples.LANGUAGE_ES)
                .content(TestUtils.asJsonString(ProductUpdateComplete)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(messages.get("error.notfound.code"))))
                .andExpect(jsonPath("$.description", is(messages.get("error.notfound.desc"))));
    }


    private static ProductUpdate initPartialProductUpdate() {
        DescriptiveNameUpdate descriptiveNameUpdate = new DescriptiveNameUpdate();
        descriptiveNameUpdate.setSingular(ProductsExamples.PRODUCT_01_NAME_ENGLISH_SINGULAR_CHANGED);
        return new ProductUpdate(descriptiveNameUpdate, null);
    }


}