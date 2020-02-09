package com.lukaszbojes.cookapp.controller;

import com.lukaszbojes.cookapp.data.dto.ShoppingListItemDto;
import com.lukaszbojes.cookapp.service.ShoppingListItemService;
import com.lukaszbojes.cookapp.util.Constants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = Constants.BASE_SHOPPING_URL)
public class ShoppingListItemController {

    private ShoppingListItemService shoppingListItemService;

    public ShoppingListItemController(ShoppingListItemService shoppingListItemService) {
        this.shoppingListItemService = shoppingListItemService;
    }

    @GetMapping(path = Constants.ALL_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllShoppingListItems(@CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return shoppingListItemService.getAllShoppingListItems(token);
    }

    @PostMapping(path = Constants.ADD_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addShoppingListItem(@RequestBody ShoppingListItemDto shoppingListItemDto,
                                                      @CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return shoppingListItemService.addShoppingListItem(shoppingListItemDto, token);
    }

    @DeleteMapping(path = Constants.DELETE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteShoppingListItem(@RequestBody ShoppingListItemDto shoppingListItemDto,
                                                         @CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return shoppingListItemService.deleteShoppingListItem(shoppingListItemDto, token);
    }

    @PutMapping(path = Constants.UPDATE_NAME_AMOUNT_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateShoppingListItem(@PathVariable String name,
                                                         @PathVariable String amount,
                                                         @RequestBody ShoppingListItemDto shoppingListItemDto,
                                                         @CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return shoppingListItemService.updateShoppingListItem(name, amount, shoppingListItemDto, token);
    }

}
