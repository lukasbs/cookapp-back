package com.lukaszbojes.cookapp.controller;

import com.lukaszbojes.cookapp.service.FavouriteService;
import com.lukaszbojes.cookapp.util.Constants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constants.BASE_FAVOURITE_URL)
public class FavouriteController {

    private FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @GetMapping(path = Constants.ALL_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllFavourites(@CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return favouriteService.getAllFavourites(token);
    }

    @PostMapping(path = Constants.ADD_NAME_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addFavourite(@PathVariable String name, @CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return favouriteService.addFavourite(name, token);
    }

    @DeleteMapping(path = Constants.DELETE_NAME_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteFavourite(@PathVariable String name, @CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return favouriteService.deleteFavourite(name, token);
    }
}
