package com.lukaszbojes.cookapp.controller;

import com.lukaszbojes.cookapp.service.FavouriteService;
import com.lukaszbojes.cookapp.util.Constraints;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constraints.BASE_FAVOURITE_URL)
public class FavouriteController {

    private FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @GetMapping(path = Constraints.ALL_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllFavourites(@RequestHeader(Constraints.AUTHORIZATION_HEADER) String token) {
        return favouriteService.getAllFavourites(token);
    }

    @PostMapping(path = Constraints.ADD_NAME_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addFavourite(@PathVariable String name, @RequestHeader(Constraints.AUTHORIZATION_HEADER) String token) {
        return favouriteService.addFavourite(name, token);
    }

    @DeleteMapping(path = Constraints.DELETE_NAME_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteFavourite(@PathVariable String name, @RequestHeader(Constraints.AUTHORIZATION_HEADER) String token) {
        return favouriteService.deleteFavourite(name, token);
    }
}
