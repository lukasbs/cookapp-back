package com.lukaszbojes.cookapp.util;

import com.lukaszbojes.cookapp.data.dto.*;
import com.lukaszbojes.cookapp.data.entity.*;
import com.lukaszbojes.cookapp.data.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.ArrayList;

public class Utils {

    public static ArrayList<UserDto> mapUserEntitiesToDtos(Iterable<User> users, ModelMapper modelMapper){
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for(User user : users){
            userDtos.add(modelMapper.map(user, UserDto.class));
        }
        return userDtos;
    }

    public static ArrayList<Ingredient> mapIngredientDtosToEntities(Iterable<IngredientDto> ingredientDtos, ModelMapper modelMapper, Recipe recipe){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for(IngredientDto ingredientDto : ingredientDtos) {
            Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
            ingredient.setRecipe(recipe);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    public static ArrayList<RecipeDto> mapRecipeEntitiesToDtos(Iterable<Recipe> recipes, ModelMapper modelMapper){
        ArrayList<RecipeDto> recipeDtos = new ArrayList<>();
        for(Recipe recipe : recipes){
            recipeDtos.add(modelMapper.map(recipe, RecipeDto.class));
        }
        return recipeDtos;
    }

    public static ArrayList<FridgeItemDto> mapFridgeItemEntitiesToDtos(Iterable<FridgeItem> fridgeItems, ModelMapper modelMapper){
        ArrayList<FridgeItemDto> fridgeItemDtos = new ArrayList<>();
        for(FridgeItem fridgeItem : fridgeItems){
            fridgeItemDtos.add(modelMapper.map(fridgeItem, FridgeItemDto.class));
        }
        return fridgeItemDtos;
    }

    public static ArrayList<ShoppingListItemDto> mapShoppingListItemEntitiesToDtos(Iterable<ShoppingListItem> shoppingListItems, ModelMapper modelMapper){
        ArrayList<ShoppingListItemDto> shoppingListItemDtos = new ArrayList<>();
        for(ShoppingListItem shoppingListItem : shoppingListItems){
            shoppingListItemDtos.add(modelMapper.map(shoppingListItem, ShoppingListItemDto.class));
        }
        return shoppingListItemDtos;
    }

    public static User getUserFromToken(String accessToken, Environment environment, UserRepository userRepository){
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(TextCodec.BASE64.decode(environment.getProperty(Constants.SECRET_KEY_PROPERTY)))
                    .parseClaimsJws(accessToken);
        } catch (Exception e) {
            System.out.println("Exception while parsing token: " + e);
            return null;
        }
        return userRepository.findByName((String)claims.getBody().get(Constants.FIELD_NAME));
    }

    public static boolean isSameUnit(String firstAmount, String secondAmount) {
        try {
            return firstAmount.split(" ")[1].toLowerCase().trim().equals(secondAmount.split(" ")[1].toLowerCase().trim());
        } catch (Exception e) {
            return false;
        }
    }

    public static String addAmounts(String firstAmount, String secondAmount) {
        try {
            int firstQuantity = Integer.parseInt(firstAmount.split(" ")[0].toLowerCase().trim());
            int secondQuantity = Integer.parseInt(secondAmount.split(" ")[0].toLowerCase().trim());
            String unit = firstAmount.split(" ")[1].toLowerCase().trim();
            return (firstQuantity + secondQuantity) + " " + unit;
        } catch(Exception e) {
            return secondAmount;
        }
    }

    public static ResponseEntity<Object> checkClient() {
        boolean result = false;
        try {
            URL url = new URL("http://localhost:4300");
            url.openStream();
            result = true;
        } catch (Exception e) {}

        if(result) {
            return new ResponseEntity<>(new MessageDto(Constants.CLIENT_AVAILABLE_ERROR_MESSAGE), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageDto(Constants.CLIENT_UNAVAILABLE_ERROR_MESSAGE), HttpStatus.NOT_FOUND);
        }
    }
}
