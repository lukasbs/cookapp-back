package com.lukaszbojes.cookapp.service;

import org.springframework.http.ResponseEntity;

public interface FavouriteService {
    ResponseEntity<Object> getAllFavourites(String token);
    ResponseEntity<Object> addFavourite(String name, String token);
    ResponseEntity<Object> deleteFavourite(String name, String token);
}
