package com.lukaszbojes.cookapp.controller;

import com.lukaszbojes.cookapp.data.dto.RecipeDto;
import com.lukaszbojes.cookapp.service.RecipeService;
import com.lukaszbojes.cookapp.util.Constants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constants.BASE_RECIPE_URL)
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(path = Constants.ALL_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping(path = Constants.SEARCH_STRING_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getRecipesByNameOrIngredients(@PathVariable String searchString) {
        return recipeService.getAllRecipesByNameOrIngredients(searchString);
    }

    @GetMapping(path = Constants.EXPLICIT_SEARCH_STRING_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getRecipesByNameOrIngredientsExplicit(@PathVariable String searchString) {
        return recipeService.getAllRecipesByNameOrIngredientsExplicit(searchString);
    }

    @GetMapping(path = Constants.GET_DAILY_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllRecipesByName() {
        return recipeService.getDailyRecipe();
    }

    @GetMapping(path = Constants.ADMIN_GET_BY_NAME_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllRecipesByName(@PathVariable String name) {
        return recipeService.getAllRecipesByName(name);
    }

    @PostMapping(path = Constants.ADMIN_ADD_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addRecipe(@RequestBody RecipeDto recipeDto) {
        return recipeService.addRecipe(recipeDto);
    }

    @PutMapping(path = Constants.ADMIN_UPDATE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateRecipe(@PathVariable String name, @RequestBody RecipeDto recipeDto) {
        return recipeService.updateRecipe(name, recipeDto);
    }

    @DeleteMapping(path = Constants.ADMIN_DELETE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteRecipe(@PathVariable String name) {
        return recipeService.deleteRecipe(name);
    }
}
