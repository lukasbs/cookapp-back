package com.lukaszbojes.cookapp.service.implementation;

import com.lukaszbojes.cookapp.data.dto.MessageDto;
import com.lukaszbojes.cookapp.data.dto.RecipeDto;
import com.lukaszbojes.cookapp.data.entity.Ingredient;
import com.lukaszbojes.cookapp.data.entity.Recipe;
import com.lukaszbojes.cookapp.data.repository.IngredientRepository;
import com.lukaszbojes.cookapp.data.repository.RecipeRepository;
import com.lukaszbojes.cookapp.service.RecipeService;
import com.lukaszbojes.cookapp.util.Constraints;
import com.lukaszbojes.cookapp.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ModelMapper modelMapper, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public ResponseEntity<Object> getAllRecipes() {
        ArrayList<RecipeDto> recipeDtos = Utils.mapRecipeEntitiesToDtos(this.recipeRepository.findAll(), this.modelMapper);
        return recipeDtos.size() > 0 ? new ResponseEntity<>(recipeDtos, HttpStatus.OK) :
                new ResponseEntity<>(new MessageDto(Constraints.RECIPE_GET_ALL_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Object> getAllRecipesByNameOrIngredients(String searchString) {
        if(searchString != null){
            Set<Recipe> resultRecipes = new HashSet<>();
            String[] words = searchString.split(",");

            resultRecipes = getRecipes(words);
            return resultRecipes.size() > 0 ? new ResponseEntity<>(Utils.mapRecipeEntitiesToDtos(resultRecipes, this.modelMapper), HttpStatus.OK) :
                    new ResponseEntity<>(new MessageDto(Constraints.RECIPE_GET_INGREDIENT_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(new MessageDto(Constraints.ERROR_MISSING_FIELDS_MESSAGE), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<Object> getAllRecipesByName(String name) {
        ArrayList<RecipeDto> recipeDtos = Utils.mapRecipeEntitiesToDtos(this.recipeRepository.findAllByNameStartingWith(name), this.modelMapper);
        return recipeDtos.size() > 0 ? new ResponseEntity<>(recipeDtos, HttpStatus.OK) :
                new ResponseEntity<>(new MessageDto(Constraints.RECIPE_GET_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Object> addRecipe(RecipeDto recipeDto) {
        if(this.recipeRepository.findByName(recipeDto.getName()) != null) {
            return new ResponseEntity<>(new MessageDto(Constraints.RECIPE_ADD_ERROR_ALREADY_ADDED_MESSAGE), HttpStatus.BAD_REQUEST);
        }
        if(recipeDto.getName() != null && recipeDto.getDescription() != null && recipeDto.getIngredients() != null){
            Recipe recipe = this.modelMapper.map(recipeDto, Recipe.class);
            for(Ingredient ingredient: recipe.getIngredients()){
                ingredient.setRecipe(recipe);
            }
            this.recipeRepository.save(recipe);
            return new ResponseEntity<>(new MessageDto(Constraints.RECIPE_ADD_SUCCESS_MESSAGE), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new MessageDto(Constraints.ERROR_MISSING_FIELDS_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> updateRecipe(String name, RecipeDto recipeDto) {
        Recipe recipe = this.recipeRepository.findByName(name);
        if(recipe != null) {
            this.ingredientRepository.deleteByRecipe(recipe);
            recipe.setName(recipeDto.getName());
            recipe.setDescription(recipeDto.getDescription());
            recipe.setIngredients(Utils.mapIngredientDtosToEntities(recipeDto.getIngredients(), this.modelMapper, recipe));

            this.recipeRepository.save(recipe);

            return new ResponseEntity<>(new MessageDto(Constraints.RECIPE_UPDATE_SUCCESS_MESSAGE), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageDto(Constraints.RECIPE_UPDATE_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> deleteRecipe(String name) {
        if(this.recipeRepository.findByName(name) != null) {
            this.recipeRepository.deleteByName(name);
            return new ResponseEntity<>(new MessageDto(Constraints.RECIPE_DELETE_SUCCESS_MESSAGE), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDto(Constraints.RECIPE_DELETE_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> getDailyRecipe() {
        return this.findDaily() != null ? new ResponseEntity<>(findDaily(), HttpStatus.OK) :
                new ResponseEntity<>(new MessageDto(Constraints.RECIPE_GET_DAILY_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
    }

    //Need refactor
    @Override
    public ResponseEntity<Object> getAllRecipesByNameOrIngredientsExplicit(String searchString) {
        if(searchString != null){
            Set<Recipe> resultRecipes = new HashSet<>();
            String[] words = searchString.split(",");

//            Set<Recipe> byNameRecipes = new HashSet<>();
//            Set<Recipe> byIngredientRecipes = new HashSet<>();
//
//            for(String word: words){
//                List<Ingredient> foundIngredients = this.ingredientRepository.findAllByNameIgnoreCaseIsContaining(word.trim().toLowerCase());
//                for(Ingredient ingredient: foundIngredients) {
//                    byIngredientRecipes.add(ingredient.getRecipe());
//                }
//            }
//
//            for(String word: words){
//                List<Recipe> recipes = this.recipeRepository.findAllByNameIgnoreCaseIsContaining(word.trim().toLowerCase());
//                byNameRecipes.addAll(recipes);
//            }

            if(words.length > 1) {
                return new ResponseEntity<>(new MessageDto(Constraints.RECIPE_GET_INGREDIENT_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
//                for(Recipe recipeN: byNameRecipes){
//                    for(Recipe recipeI: byIngredientRecipes){
//                        if(recipeN.getRecipeID() == recipeI.getRecipeID())
//                            resultRecipes.add(recipeN);
//                    }
//                }
            } else if(words.length == 1){
                resultRecipes = getRecipes(words);
                if(resultRecipes.size() > 0){
                    return new ResponseEntity<>(Utils.mapRecipeEntitiesToDtos(resultRecipes, this.modelMapper), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new MessageDto(Constraints.RECIPE_GET_INGREDIENT_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<>(new MessageDto(Constraints.ERROR_MISSING_FIELDS_MESSAGE), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new MessageDto(Constraints.ERROR_MISSING_FIELDS_MESSAGE), HttpStatus.BAD_REQUEST);
        }

    }

    private Set<Recipe> getRecipes(String[] words){
        Set<Recipe> resultRecipes = new HashSet<>();

        for(String word: words) {
            List<Ingredient> foundIngredients = this.ingredientRepository.findAllByNameIgnoreCaseIsContaining(word.trim().toLowerCase());
            for(Ingredient ingredient: foundIngredients) {
                resultRecipes.add(ingredient.getRecipe());
            }

            List<Recipe> recipes = this.recipeRepository.findAllByNameIgnoreCaseIsContaining(word.trim().toLowerCase());
            resultRecipes.addAll(recipes);
        }
        return resultRecipes;
    }

    private RecipeDto findDaily() {
        List<Recipe> recipes = (List<Recipe>) this.recipeRepository.findAll();
        if(recipes.size() > 0) {
            Date date = new Date();
            int dateNumber = date.getYear() + date.getMonth() + date.getDay();
            dateNumber = dateNumber % recipes.size();
            return modelMapper.map(recipes.get(dateNumber), RecipeDto.class);
        } else {
            return null;
        }
    }
}
