package com.ojodev.cookinghero.recipes.business;

import com.ojodev.cookinghero.recipes.api.model.Ingredient;
import com.ojodev.cookinghero.recipes.config.Messages;
import com.ojodev.cookinghero.recipes.domain.constants.RecipesConstants;
import com.ojodev.cookinghero.recipes.domain.exception.ApiBadRequestException;
import com.ojodev.cookinghero.recipes.domain.exception.ApiException;
import com.ojodev.cookinghero.recipes.domain.exception.NotFoundException;
import com.ojodev.cookinghero.recipes.domain.model.*;
import com.ojodev.cookinghero.recipes.infrastructure.po.*;
import com.ojodev.cookinghero.recipes.infrastructure.repository.IngredientsRepository;
import com.ojodev.cookinghero.recipes.infrastructure.repository.MeasuresRepository;
import com.ojodev.cookinghero.recipes.infrastructure.repository.ProductsRepository;
import com.ojodev.cookinghero.recipes.infrastructure.repository.RecipesRepository;
import com.ojodev.cookinghero.recipes.mapper.DescriptiveNameMapper;
import com.ojodev.cookinghero.recipes.mapper.IngredientsMapper;
import com.ojodev.cookinghero.recipes.mapper.LanguageEnumMapper;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.shiro.session.mgt.DelegatingSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IngredientsBusinessImpl implements IngredientsBusiness {

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private RecipesRepository recipesRepository;

    @Autowired
    private MeasuresRepository measuresRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private IngredientsMapper ingredientsMapper;

    @Autowired
    private DescriptiveNameMapper descriptiveNameMapper;

    @Autowired
    private LanguageEnumMapper languageEnumMapper;


    @Autowired
    private Messages messages;

    @Override
    public List<IngredientBO> getIngredients(String recipeId) throws NotFoundException {
        List<RecipePO> recipes = recipesRepository.findByObjectId(recipeId);
        throwErrorIfRecipeNotExists(recipes);
        return ingredientsMapper.toIngredientBOList(ingredientsRepository.findByRecipeId(recipeId), LanguageEnumBO.fromValue(recipes.get(0).getLanguage()));
    }


    @Override
    public Optional<IngredientBO> getIngredient(String recipeId, String ingredientId) throws NotFoundException {
        if (StringUtils.isEmpty(recipeId) || StringUtils.isEmpty(ingredientId)) {
            return Optional.empty();
        }
        List<RecipePO> recipes = recipesRepository.findByObjectId(recipeId);
        throwErrorIfRecipeNotExists(recipes);
        if (recipes.get(0).getIngredients() == null || recipes.get(0).getIngredients().isEmpty()) {
            return Optional.empty();
        }
        List<IngredientPO> ingredientPOList = recipes.get(0).getIngredients().stream().filter(i -> ingredientId.equals(i.getObjectId())).collect(Collectors.toList());
        if (ingredientPOList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(ingredientsMapper.toIngredientBO(ingredientPOList.get(0), LanguageEnumBO.fromValue(recipes.get(0).getLanguage())));
    }

    @Override
    public void addIngredient(IngredientNewBO ingredient) throws ApiException {
        RecipePO recipe = getRecipeOrThrowErrorIfRecipeNotExists(ingredient.getRecipeId());
        throwErrorIfProductExistsInRecipe(ingredient, recipe);
        MeasurePO measure = getMeasureOrThrowErrorIfNotExists(ingredient.getMeasureId());
        ProductPO product = getProductOrCreateNewProductIfNotExists(ingredient.getProductName(), ingredient.getQuantity(), LanguageEnumBO.fromValue(recipe.getLanguage()));
        IngredientPO ingredientPO = new IngredientPO(ingredient.getId(), product, ingredient.getQuantity(), measure);
        ingredientPO.setRecipe(recipe);
        ingredientsRepository.save(ingredientPO);

    }

    @Override
    public void addOrReplaceIngredient(IngredientNewBO ingredient) throws ApiException {
        RecipePO recipe = getRecipeOrThrowErrorIfRecipeNotExists(ingredient.getRecipeId());
        MeasurePO measure = getMeasureOrThrowErrorIfNotExists(ingredient.getMeasureId());
        ProductPO product = getProductOrCreateNewProductIfNotExists(ingredient.getProductName(), ingredient.getQuantity(), LanguageEnumBO.fromValue(recipe.getLanguage()));
        IngredientPO ingredientPO = createOrReplaceIngredientPO(recipe, ingredient.getId(), product, ingredient.getQuantity(), measure);
        ingredientPO.setRecipe(recipe);
        ingredientsRepository.save(ingredientPO);
    }

    /**
     * If not exist an ingredient with the same name (singular or plural) in the recipe, create a new Ingredient.
     * If exists an ingredient with the same name (singular or plural) in the recipe, modify this Ingredient.
     *
     * @param recipe recipe
     * @param objectId ingredient objectId
     * @param product ingredient product
     * @param quantity ingredient quantity
     * @param measure ingredient measure
     * @return new or modified existent ingredient
     */
    private IngredientPO createOrReplaceIngredientPO(RecipePO recipe, String objectId, ProductPO product, BigDecimal quantity, MeasurePO measure) {
        IngredientPO newOrExistingIngredient = new IngredientPO(objectId, product, quantity, measure);
        if (recipe != null && recipe.getIngredients() != null && recipe.getLanguage() != null && product != null && product.getNames() != null)  {
            List<DescriptiveNamePO> productNamesInRecipeLanguage = product.getNames().stream().filter(n -> n.getLanguage()!= null && n.getLanguage().equals(recipe.getLanguage())).collect(Collectors.toList());
            if (!productNamesInRecipeLanguage.isEmpty()) {
                for (IngredientPO ingredientInRecipe : recipe.getIngredients()) {
                    if (ingredientContainsProductName(ingredientInRecipe,  productNamesInRecipeLanguage.get(0))) {
                        newOrExistingIngredient.setId(ingredientInRecipe.getId());
                        return newOrExistingIngredient;
                    }
                }
            }
        }
        return newOrExistingIngredient;
    }

    private boolean ingredientContainsProductName(IngredientPO ingredientInRecipe, DescriptiveNamePO productNameInRecipeLanguage) {
        if (ingredientInRecipe.getProduct() != null && ingredientInRecipe.getProduct().getNames() != null) {
            for (DescriptiveNamePO descriptiveNameInProduct : ingredientInRecipe.getProduct().getNames()) {
                if (StringUtils.equals(productNameInRecipeLanguage.getSingular(), descriptiveNameInProduct.getSingular()) ||
                        StringUtils.equals(productNameInRecipeLanguage.getPlural(), descriptiveNameInProduct.getPlural()))
                    return true;
            }
        }
        return false;
    }

    private void throwErrorIfProductExistsInRecipe(IngredientNewBO ingredient, RecipePO recipe) throws ApiBadRequestException {
        if (recipe != null && ingredient != null && recipe.getIngredients() != null && ingredient.getProductName() != null && recipe.getLanguage() != null) {
            for (IngredientPO ingredientInRecipe : recipe.getIngredients()) {
                if (ingredientInRecipe.getProduct() != null && ingredientInRecipe.getProduct().getNames() != null) {
                    for (DescriptiveNamePO descriptiveNameInProduct : ingredientInRecipe.getProduct().getNames()) {
                        if (StringUtils.equals(ingredient.getProductName(), descriptiveNameInProduct.getSingular()) ||
                                StringUtils.equals(ingredient.getProductName(), descriptiveNameInProduct.getPlural()))
                            throw new ApiBadRequestException(messages.get("error.ingredient.duplicateinrecipe.code"), messages.get("error.ingredient.duplicateinrecipe.desc", ingredient.getProductName()));
                    }
                }
            }
        }
    }

    @Override
    public void deleteIngredient(String recipeId, String ingredientId) throws NotFoundException {
        if (StringUtils.isEmpty(recipeId) || StringUtils.isEmpty(ingredientId)) {
            throw new NotFoundException(messages.get("error.notfound.recipe.code"), messages.get("error.notfound.recipe.desc"));
        }
        List<RecipePO> recipes = recipesRepository.findByObjectId(recipeId);
        throwErrorIfRecipeNotExists(recipes);
        List<IngredientPO> ingredientPOList = recipes.get(0).getIngredients().stream().filter(i -> ingredientId.equals(i.getObjectId())).collect(Collectors.toList());
        if (ingredientPOList.isEmpty()) {
            throw new NotFoundException(messages.get("error.notfound.ingredient.code"), messages.get("error.notfound.ingredient.desc"));
        }
        ingredientsRepository.deleteByObjectId(ingredientId);
    }


    private void throwErrorIfRecipeNotExists(List<RecipePO> recipes) throws NotFoundException {
        if (recipes == null || recipes.isEmpty()) {
            throw new NotFoundException(messages.get("error.notfound.recipe.code"), messages.get("error.notfound.recipe.desc"));
        }
    }


    private RecipePO getRecipeOrThrowErrorIfRecipeNotExists(String recipeId) throws NotFoundException {
        List<RecipePO> recipePOList = new ArrayList<>();
        if (!StringUtils.isEmpty(recipeId)) {
            recipePOList = recipesRepository.findByObjectId(recipeId);
        }
        if (recipePOList.isEmpty()) {
            throw new NotFoundException(messages.get("error.notfound.recipe.code"), messages.get("error.notfound.recipe.desc"));
        } else {
            return recipePOList.get(0);
        }
    }

    private MeasurePO getMeasureOrThrowErrorIfNotExists(String measureId) throws NotFoundException {
        if (StringUtils.isEmpty(measureId)) {
            return null;
        } else {

            List<MeasurePO> measurePOList = measuresRepository.findByObjectId(measureId);
            if (measurePOList.isEmpty()) {
                throw new NotFoundException(messages.get("error.notfound.measure.code"), messages.get("error.notfound.measure.desc"));
            } else {
                return measurePOList.get(0);
            }
        }

    }

    /**
     * Search a product by product.id
     * If product.id is null, search a product by product.name.singular or product.name.plural, into APPROVED_BY_ADMIN products
     * If is null, return a new product with entry product attributes
     *
     * @param productName
     * @param quantity
     * @param language
     * @return
     * @throws NotFoundException
     */
    private ProductPO getProductOrCreateNewProductIfNotExists(String productName, BigDecimal quantity, LanguageEnumBO language) {
        ProductPO productPO = null;
        if (!StringUtils.isEmpty(productName)) {
            List<ProductPO> productList = productsRepository.findProducts(productName,
                    ProductStatusEnumBO.APPROVED_BY_ADMIN.toString(),
                    setDefaultLanguageIfNull(language).toString(), 0, 1);
            if (productList.isEmpty()) {
                DescriptiveNamePO descriptiveNamePO = new DescriptiveNamePO();
                descriptiveNamePO.setLanguage(language.toString());
                if (quantity != null && quantity.compareTo(BigDecimal.ONE) > 1) {
                    descriptiveNamePO.setPlural(productName);
                } else {
                    descriptiveNamePO.setSingular(productName);
                }
                productPO = new ProductPO(new IdentifiableBO(productName).getId(), Arrays.asList(descriptiveNamePO), ProductStatusEnumBO.CREATED_BY_USER.toString());
            } else {
                productPO = productList.get(0);
            }
        }
        return productPO;
    }

    private LanguageEnumBO setDefaultLanguageIfNull(LanguageEnumBO language) {
        return language == null ? RecipesConstants.DEFAULT_LANGUAGE : language;
    }

}