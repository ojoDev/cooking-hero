package com.ojodev.cookinghero.recipes.api.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;


/**
 * Define a name to describe a item associated with a quantity, in singular or plural form.
 */
@ApiModel(description = "Define a name to describe a item associated with a quantity, in singular or plural form.")
@Validated
public class DescriptiveNameUpdate {

    @JsonProperty("singular")
    @ApiModelProperty(example = "potato", value = "Singular name.")
    private Optional<String> singular = Optional.empty();

    @JsonProperty("plural")
    @ApiModelProperty(example = "potatoes", value = "Plural name.")
    private Optional<String> plural = Optional.empty();


    public DescriptiveNameUpdate() {
    }

    public DescriptiveNameUpdate(String singular, String plural) {
        this.singular = Optional.of(singular);
        this.plural = Optional.of(plural);
    }

    public String getSingular() {
        return (singular.isPresent() ? singular.get() : null );
    }
    @ApiModelProperty(hidden = true)
    public Optional<String> getSingularOpt() {
        return singular;
    }

    @ApiModelProperty(hidden = true)
    public Optional<String> getPluralOpt() {
        return plural;
    }

    public void setSingular(String singular) {
        this.singular = singular == null ? null : Optional.of(singular);
    }

    public String getPlural() {
        return (plural.isPresent() ? plural.get() : null);
    }

    public void setPlural(String plural) {
        this.plural = plural == null ? null : Optional.of(plural);
    }
}