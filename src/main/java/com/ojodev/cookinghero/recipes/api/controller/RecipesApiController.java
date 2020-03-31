package com.ojodev.cookinghero.recipes.api.controller;

import com.ojodev.cookinghero.recipes.api.model.Recipe;
import com.ojodev.cookinghero.recipes.api.model.RecipeNew;
import com.ojodev.cookinghero.recipes.api.model.RecipeStatusEnum;
import com.ojodev.cookinghero.recipes.api.model.RecipeUpdate;
import com.ojodev.cookinghero.recipes.business.RecipesBusiness;
import com.ojodev.cookinghero.recipes.config.Messages;
import com.ojodev.cookinghero.recipes.domain.exception.NotFoundException;
import com.ojodev.cookinghero.recipes.domain.model.RecipeBO;
import com.ojodev.cookinghero.recipes.domain.model.RecipeNewBO;
import com.ojodev.cookinghero.recipes.mapper.RecipesMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.List;

@Controller
@Api(tags = "recipes", description = "Hero recipes")
public class RecipesApiController implements RecipesApi {


    @Autowired
    private RecipesBusiness recipesBusiness;

    @Autowired
    private RecipesMapper recipesMapper;

    @Autowired
    private Messages messages;

    @Override
    public ResponseEntity<List<Recipe>> getRecipes(@ApiParam(value = "Recipe name, partial searches allowed.") @Valid @RequestParam(value = "name", required = false) String name,
                                                   @ApiParam(value = "User id that create the recipe.") @Valid @RequestParam(value = "userId", required = false) String userId,
                                                   @ApiParam(value = "Recipe status.") @Valid @RequestParam(value = "status", required = false) RecipeStatusEnum status,
                                                   @ApiParam(value = "Recipe fields returned in response, separated by ','.") @Valid @RequestParam(value = "fields", required = false) String fields,
                                                   @Min(1) @Max(100) @ApiParam(value = "Maximum number of records returned, by default 10.", defaultValue = "10") @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                   @ApiParam(value = "Cursor for pagination. Include in the API call to point to an specific recipe.") @Valid @RequestParam(value = "cursor", required = false) String cursor) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> addRecipe(@ApiParam(value = "Recipe to add.") @Valid @RequestBody RecipeNew body) {
        RecipeNewBO recipeNewBO = recipesMapper.toRecipeNewBO(body);
        recipesBusiness.addRecipe(recipeNewBO);
        return ResponseEntity.created(generateLocationHeader(recipeNewBO.getId())).build();
    }

    public ResponseEntity<Recipe> getRecipe(@ApiParam(value = "Recipe id.", required = true) @PathVariable("recipe-id") String recipeId) throws NotFoundException {
        RecipeBO recipeBO = recipesBusiness.getRecipe(recipeId).orElseThrow(() -> new NotFoundException(messages.get("error.notfound.recipe.code"),messages.get("error.notfound.recipe.desc")));;
        return ResponseEntity.status(HttpStatus.OK)
                .body(recipesMapper.toRecipe(recipeBO));
    }

    @Override
    public ResponseEntity<Void> updateRecipe(@ApiParam(value = "Recipe id.", required = true) @PathVariable("recipe-id") String recipeId,
                                             @ApiParam(value = "Recipe to update.") @Valid @RequestBody RecipeUpdate body) {
        //TODO Hacer
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(@ApiParam(value = "Recipe id.", required = true) @PathVariable("recipe-id") String recipeId) {
        //TODO Hacer
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    private URI generateLocationHeader(String id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
