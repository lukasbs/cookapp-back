package com.lukaszbojes.cookapp.controller;

import com.lukaszbojes.cookapp.data.dto.ShoppingListItemDto;
import com.lukaszbojes.cookapp.service.ShoppingListItemService;
import com.lukaszbojes.cookapp.util.Constraints;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constraints.BASE_SHOPPING_URL)
public class ShoppingListItemController {

    private ShoppingListItemService shoppingListItemService;

    public ShoppingListItemController(ShoppingListItemService shoppingListItemService) {
        this.shoppingListItemService = shoppingListItemService;
    }

    @GetMapping(path = Constraints.ALL_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllShoppingListItems(@RequestHeader(Constraints.AUTHORIZATION_HEADER) String token) {
        return shoppingListItemService.getAllShoppingListItems(token);
    }

    @PostMapping(path = Constraints.ADD_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addShoppingListItem(@RequestBody ShoppingListItemDto shoppingListItemDto,
                                                @RequestHeader(Constraints.AUTHORIZATION_HEADER) String token) {
        return shoppingListItemService.addShoppingListItem(shoppingListItemDto, token);
    }

    @DeleteMapping(path = Constraints.DELETE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteShoppingListItem(@RequestBody ShoppingListItemDto shoppingListItemDto,
                                                         @RequestHeader(Constraints.AUTHORIZATION_HEADER) String token) {
        return shoppingListItemService.deleteShoppingListItem(shoppingListItemDto, token);
    }

    @PutMapping(path = Constraints.UPDATE_NAME_AMOUNT_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateShoppingListItem(@PathVariable String name,
                                                         @PathVariable String amount,
                                                         @RequestBody ShoppingListItemDto shoppingListItemDto,
                                                         @RequestHeader(Constraints.AUTHORIZATION_HEADER) String token) {
        return shoppingListItemService.updateShoppingListItem(name, amount, shoppingListItemDto, token);
    }

}
