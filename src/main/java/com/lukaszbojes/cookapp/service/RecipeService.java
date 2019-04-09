package com.lukaszbojes.cookapp.service;

import com.lukaszbojes.cookapp.data.dto.RecipeDto;
import org.springframework.http.ResponseEntity;

public interface RecipeService {
    ResponseEntity<Object> getAllRecipes();
    ResponseEntity<Object> getAllRecipesByNameOrIngredients(String searchString);
    ResponseEntity<Object> getAllRecipesByName(String name);
    ResponseEntity<Object> addRecipe(RecipeDto recipeDto);
    ResponseEntity<Object> updateRecipe(String name, RecipeDto recipeDto);
    ResponseEntity<Object> deleteRecipe(String name);
    ResponseEntity<Object> getDailyRecipe();
    ResponseEntity<Object> getAllRecipesByNameOrIngredientsExplicit(String searchString);
}
