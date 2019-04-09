package com.lukaszbojes.cookapp.util;

public class Constraints {

    public static final String API_URL = "/api";
    public static final String ADMIN_URL = "/admin";
    public static final String GET_URL = "/get";
    public static final String UPDATE_URL = "/update";
    public static final String DELETE_URL = "/delete";
    public static final String ALL_URL = "/all";
    public static final String NAME_PARAM_URL = "/{name}";
    public static final String ADD_URL = "/add";
    public static final String AUTH_URL = "/auth";

    public static final String BASE_USER_URL = API_URL + "/user";
    public static final String BASE_RECIPE_URL = API_URL + "/recipe";
    public static final String BASE_FRIDGE_URL = API_URL + "/fridge";
    public static final String BASE_SHOPPING_URL = API_URL + "/shopping";
    public static final String BASE_FAVOURITE_URL = API_URL + "/favourite";
    public static final String REGISTER_URL = AUTH_URL + "/register";
    public static final String LOGIN_URL = AUTH_URL + "/login";
    public static final String ADMIN_LOGIN_URL = ADMIN_URL + LOGIN_URL;
    public static final String SEARCH_STRING_URL = GET_URL + "/{searchString}";
    public static final String GET_DAILY_URL = GET_URL + "/daily";
    public static final String EXPLICIT_SEARCH_STRING_URL = "/explicit" + SEARCH_STRING_URL;
    public static final String UPDATE_NAME_URL = UPDATE_URL + NAME_PARAM_URL;
    public static final String UPDATE_NAME_AMOUNT_URL = UPDATE_NAME_URL + "/{amount}";
    public static final String ADD_NAME_URL = ADD_URL + NAME_PARAM_URL;
    public static final String DELETE_NAME_URL = DELETE_URL + NAME_PARAM_URL;

    public static final String ADMIN_DELETE_URL = ADMIN_URL + DELETE_URL + NAME_PARAM_URL;
    public static final String ADMIN_GET_ALL_URL = ADMIN_URL + ALL_URL;
    public static final String ADMIN_GET_BY_NAME_URL = ADMIN_URL + GET_URL + NAME_PARAM_URL;
    public static final String ADMIN_UPDATE_URL = ADMIN_URL +  UPDATE_URL + NAME_PARAM_URL;
    public static final String ADMIN_ADD_URL = ADMIN_URL + ADD_URL;

    public static final String REGISTERED_SUCCESS_MESSAGE = "User successfully registered!";
    public static final String EMPTY_CREDENTIALS_MESSAGE = "You need to provide name and password!";
    public static final String ALREADY_REGISTERED_MESSAGE = "User already registered!";
    public static final String LOGIN_FAILED_MESSAGE = "Incorrect name or password!";
    public static final String USER_GET_ALL_ERROR_MESSAGE = "No users found!";
    public static final String USER_GET_ERROR_MESSAGE = "User with provided name not found!";
    public static final String USER_UPDATE_SUCCESS_MESSAGE = "User successfully updated!";
    public static final String USER_UPDATE_ERROR_MESSAGE = "User with provided name wasn't found, cannot update user!";
    public static final String USER_ADD_SUCCESS_MESSAGE = "User successfully added!";
    public static final String USER_ADD_ERROR_ALREADY_ADDED_MESSAGE = "User already added to database!";
    public static final String USER_DELETE_ERROR_MESSAGE = "User with provided name wasn't found, cannot delete user!";
    public static final String USER_DELETE_SUCCESS_MESSAGE = "User successfully deleted!";

    public static final String RECIPE_GET_ALL_ERROR_MESSAGE = "No recipes found!";
    public static final String RECIPE_ADD_ERROR_ALREADY_ADDED_MESSAGE = "Recipe already added to database!";
    public static final String RECIPE_ADD_SUCCESS_MESSAGE = "Recipe successfully added!";
    public static final String RECIPE_DELETE_SUCCESS_MESSAGE = "Recipe successfully deleted!";
    public static final String RECIPE_DELETE_ERROR_MESSAGE = "Recipe with provided name wasn't found, cannot delete recipe!";
    public static final String RECIPE_GET_ERROR_MESSAGE = "Recipes with provided name not found!";
    public static final String RECIPE_UPDATE_SUCCESS_MESSAGE = "Recipe successfully updated!";
    public static final String RECIPE_UPDATE_ERROR_MESSAGE = "Recipe with provided name wasn't found, cannot update recipe!";
    public static final String RECIPE_GET_INGREDIENT_ERROR_MESSAGE = "Recipes with provided name or ingredients not found!";
    public static final String RECIPE_GET_DAILY_ERROR_MESSAGE = "No recipes in database, cannot return daily recipe!";

    public static final String FRIDGE_GET_ALL_ERROR_MESSAGE = "No fridge items found!";
    public static final String FRIDGE_ADD_SUCCESS_MESSAGE = "Fridge item successfully added!";
    public static final String FRIDGE_DELETE_SUCCESS_MESSAGE = "Fridge item successfully deleted!";
    public static final String FRIDGE_DELETE_ERROR_MESSAGE = "Fridge item with provided info wasn't found, cannot delete item!";

    public static final String SHOPPING_GET_ALL_ERROR_MESSAGE = "No shopping list items found!";
    public static final String SHOPPING_ADD_SUCCESS_MESSAGE = "Shopping list item successfully added!";
    public static final String SHOPPING_DELETE_SUCCESS_MESSAGE = "Shopping list item successfully deleted!";
    public static final String SHOPPING_DELETE_ERROR_MESSAGE = "Shopping list item with provided info wasn't found, cannot delete item!";
    public static final String SHOPPING_UPDATE_SUCCESS_MESSAGE = "Shopping list item successfully updated!";
    public static final String SHOPPING_UPDATE_ERROR_MESSAGE = "Shopping list item with provided info wasn't found, cannot update item!";

    public static final String FAVOURITE_ADD_SUCCESS_MESSAGE = "Favourite recipe successfully added!";
    public static final String FAVOURITE_ADD_ERROR_NOT_FOUND_MESSAGE = "User or recipe not found, cannot add favourite recipe!";
    public static final String FAVOURITE_ADD_ERROR_ALREADY_ADDED_MESSAGE = "Favourite recipe already added to database for this user!";
    public static final String FAVOURITE_DELETE_SUCCESS_MESSAGE = "Favourite recipe successfully deleted!";
    public static final String FAVOURITE_DELETE_ERROR_MESSAGE = "Favourite recipe with provided info wasn't found, cannot delete!";

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_PASSWORD = "password";
    public static final String SECRET_KEY_PROPERTY = "auth.secret.key";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ERROR_MISSING_FIELDS_MESSAGE = "Missing fields in provided request!";
}
