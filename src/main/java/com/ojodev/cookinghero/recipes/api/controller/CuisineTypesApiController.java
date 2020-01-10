package com.ojodev.cookinghero.recipes.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.ojodev.cookinghero.recipes.api.model.*;
import com.ojodev.cookinghero.recipes.business.CuisineTypesBusiness;
import com.ojodev.cookinghero.recipes.config.Messages;
import com.ojodev.cookinghero.recipes.domain.exception.*;
import com.ojodev.cookinghero.recipes.domain.model.CuisineTypeBO;
import com.ojodev.cookinghero.recipes.mapper.CuisineTypesMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Api(tags = "cuisine-types", description = "Cuisine types API")
public class CuisineTypesApiController implements CuisineTypesApi {

    @Autowired
    private CuisineTypesBusiness cuisineTypesBusiness;

    @Autowired
    private CuisineTypesMapper cuisineTypeMapper;

    @Autowired
    private Messages messages;

    private static final Logger log = LoggerFactory.getLogger(CuisineTypesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public CuisineTypesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<CuisineType>> getCuisineTypes(
            @ApiParam(value = "User need to choose a language to receive data.", required = true) @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = true) String acceptLanguage,
            @ApiParam(value = "Cuisine type name. Partial searches allowed.") @Valid @RequestParam(value = "name", required = false) String name) throws ApiException {

        checkAccept(request.getHeader(HttpHeaders.ACCEPT));
        checkAcceptedLanguage(acceptLanguage);

        return  ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_LANGUAGE, acceptLanguage)
                .body(cuisineTypeMapper.toCuisineTypeList(cuisineTypesBusiness.getCuisineTypes(name, LanguageEnum.fromValue(acceptLanguage))));

    }

    public ResponseEntity<Void> addCuisineType(@ApiParam(value = "Cuisine type to add.") @Valid @RequestBody CuisineTypeNew body) {
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<CuisineType> getCuisineType(@ApiParam(value = "Cuisine type id.", required = true) @PathVariable("cuisine-type-id") String cuisineTypeId, @ApiParam(value = "User need to choose a language to receive data.", required = true) @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = true) String acceptLanguage) throws ApiException {

        checkAccept(request.getHeader(HttpHeaders.ACCEPT));
        checkAcceptedLanguage(acceptLanguage);

        Optional<CuisineTypeBO> cuisineTypeBOOpt = cuisineTypesBusiness.getCuisineType(cuisineTypeId, LanguageEnum.fromValue(acceptLanguage));
        if (cuisineTypeBOOpt.isPresent()) {
            throw new NotFoundException();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_LANGUAGE, acceptLanguage)
                .body(cuisineTypeMapper.toCuisineType(cuisineTypeBOOpt.get()));
    }

    public ResponseEntity<Void> updateCuisineType(@ApiParam(value = "User need to choose a language to receive data.", required = true) @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = true) String acceptLanguage, @ApiParam(value = "Cuisine type id.", required = true) @PathVariable("cuisine-type-id") String cuisineTypeId, @ApiParam(value = "CuisineType to update.") @Valid @RequestBody CuisineType body) {
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<Void> deleteCuisineType(@ApiParam(value = "Cuisine type id.", required = true) @PathVariable("cuisine-type-id") String cuisineTypeId) {
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
    }

    private void checkAcceptedLanguage(String acceptLanguage) throws ApiFieldsException {
        if (LanguageEnum.fromValue(acceptLanguage) == null) {
            throw new ApiFieldsException(messages.get("error.badrequest.invalidparams.code"), messages.get("error.badrequest.invalidparams.desc"),
                    Arrays.asList(new FieldError(messages.get("error.badrequest.invalidparams.fields.headerparaminvalid.code"), HttpHeaders.ACCEPT_LANGUAGE, HttpHeaders.ACCEPT_LANGUAGE + " " + messages.get("error.badrequest.invalidparams.fields.headerparaminvalid.desc.enum") + " " + LanguageEnum.getValueList())));
        }
    }

    private void checkAccept(String accept) throws ApiAcceptException {
        if (accept != null && !accept.equals(MediaType.APPLICATION_JSON_VALUE)) {
            throw new ApiAcceptException();
        }
    }

}