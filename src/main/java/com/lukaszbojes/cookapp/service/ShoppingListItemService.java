package com.lukaszbojes.cookapp.service;

import com.lukaszbojes.cookapp.data.dto.ShoppingListItemDto;
import org.springframework.http.ResponseEntity;

public interface ShoppingListItemService {
    ResponseEntity<Object> getAllShoppingListItems(String token);
    ResponseEntity<Object> addShoppingListItem(ShoppingListItemDto shoppingListItemDto, String token);
    ResponseEntity<Object> deleteShoppingListItem(ShoppingListItemDto shoppingListItemDto, String token);
    ResponseEntity<Object> updateShoppingListItem(String name, String amount, ShoppingListItemDto shoppingListItemDto, String token);
}
