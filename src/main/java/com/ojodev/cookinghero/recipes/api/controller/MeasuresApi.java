package com.ojodev.cookinghero.recipes.api.controller;

import com.google.common.net.HttpHeaders;
import com.ojodev.cookinghero.recipes.api.model.*;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "measures")
public interface MeasuresApi {

    @ApiOperation(value = "Get measures", nickname = "getMeasures", notes = "Search measures in a specific language. ", response = Measure.class, responseContainer = "List", tags={ "measures"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Measure list.", response = Measure.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad input parameter.", response = ApiFieldsError.class),
            @ApiResponse(code = 401, message = "The request has not been applied because it lacks valid authentication credentials for the target resource.", response = ApiError.class),
            @ApiResponse(code = 403, message = "The server understood the request but refuses to authorize it.", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ApiError.class) })
    @GetMapping(value = "/measures",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<List<Measure>> getMeasures(@ApiParam(value = "User need to choose a language to receive data. Valid values are: en, es.", required = true) @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = true) String acceptLanguage);


    @ApiOperation(value = "Add a measure", nickname = "addMeasure", notes = "Add a new measure.\nYou can add multiple languages in a single request. English (en) is mandatory. ", tags = { "measures"})
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Resource created."),
        @ApiResponse(code = 400, message = "Bad input parameter.", response = ApiFieldsError.class),
        @ApiResponse(code = 401, message = "The request has not been applied because it lacks valid authentication credentials for the target resource.", response = ApiError.class),
        @ApiResponse(code = 403, message = "The server understood the request but refuses to authorize it.", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error.", response = ApiError.class) })
    @PostMapping(value = "/measures",
        produces = { MediaType.APPLICATION_JSON_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<Void> addMeasure(@ApiParam(value = "Measure to add.") @Valid @RequestBody MeasureNew body);


    @ApiOperation(value = "Get a measure by id", nickname = "getMeasure", notes = "Search for a measure in a specific language.", response = Measure.class, tags={ "measures"})
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Measure.", response = Measure.class),
        @ApiResponse(code = 400, message = "Bad input parameter.", response = ApiFieldsError.class),
        @ApiResponse(code = 401, message = "The request has not been applied because it lacks valid authentication credentials for the target resource.", response = ApiError.class),
        @ApiResponse(code = 403, message = "The server understood the request but refuses to authorize it.", response = ApiError.class),
        @ApiResponse(code = 404, message = "Not found.", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error.", response = ApiError.class) })
    @GetMapping(value = "/measures/{measure-id}",
        produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<Measure> getMeasure(@ApiParam(value = "Measure id.", required = true) @PathVariable("measure-id") String measureId,
                                       @ApiParam(value = "User need to choose a language to receive data. Valid values are: en, es.", required = true) @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String acceptLanguage
    );


    @ApiOperation(value = "Update a measure", nickname = "updateMeasure", notes = "Update a measure description.\nYou can add more languages to a exist cuisine type with Accept-Language header. ", tags={ "measures"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Measure description updated."),
            @ApiResponse(code = 400, message = "Bad input parameter.", response = ApiFieldsError.class),
            @ApiResponse(code = 401, message = "The request has not been applied because it lacks valid authentication credentials for the target resource.", response = ApiError.class),
            @ApiResponse(code = 403, message = "The server understood the request but refuses to authorize it.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not found.", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ApiError.class) })
    @PatchMapping(value = "/measures/{measure-id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<Void> updateMeasure(@ApiParam(value = "User need to choose a language to receive data. Valid values are: en, es.", required = true) @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE) String acceptLanguage,
                                       @ApiParam(value = "Measure id.", required = true) @PathVariable("measure-id") String measureId,
                                       @ApiParam(value = "Measure to update.") @Valid @RequestBody MeasureUpdate body
    );


    @ApiOperation(value = "Delete a measure", nickname = "deleteMeasure", notes = "Delete a measure.", tags={ "measures"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Measure deleted."),
            @ApiResponse(code = 401, message = "The request has not been applied because it lacks valid authentication credentials for the target resource.", response = ApiError.class),
            @ApiResponse(code = 403, message = "The server understood the request but refuses to authorize it.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not found.", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ApiError.class) })
    @RequestMapping(value = "/measures/{measure-id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMeasure(@ApiParam(value = "Measure id.", required = true) @PathVariable("measure-id") String measureId
    );




}
