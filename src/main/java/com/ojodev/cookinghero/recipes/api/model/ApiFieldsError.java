package com.ojodev.cookinghero.recipes.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * API  error with fields
 */
@ApiModel(description = "API error with fields")
@Validated
@Data
@NoArgsConstructor
public class ApiFieldsError extends ApiError {

    public ApiFieldsError(String code, String description, List<ApiFieldError> fields) {
        super(code, description);
        this.fields = fields;
    }

    @JsonProperty("fields")
    @ApiModelProperty(value = "Multiple error field")
    private List<ApiFieldError> fields;

}
