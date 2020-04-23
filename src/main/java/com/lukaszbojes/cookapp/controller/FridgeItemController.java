package com.lukaszbojes.cookapp.controller;

import com.lukaszbojes.cookapp.data.dto.FridgeItemDto;
import com.lukaszbojes.cookapp.service.FridgeItemService;
import com.lukaszbojes.cookapp.util.Constants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = Constants.BASE_FRIDGE_URL)
public class FridgeItemController {

    private FridgeItemService fridgeItemService;

    public FridgeItemController(FridgeItemService fridgeItemService) {
        this.fridgeItemService = fridgeItemService;
    }

    @GetMapping(path = Constants.ALL_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllFridgeItems(@CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return fridgeItemService.getAllFridgeItems(token);
    }

    @PostMapping(path = Constants.ADD_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addFridgeItem(@RequestBody FridgeItemDto fridgeItemDto, @CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return fridgeItemService.addFridgeItem(fridgeItemDto, token);
    }

    @DeleteMapping(path = Constants.DELETE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteFridgeItem(@RequestBody FridgeItemDto fridgeItemDto, @CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return fridgeItemService.deleteFridgeItem(fridgeItemDto, token);
    }

    @PutMapping(path = Constants.UPDATE_NAME_AMOUNT_DATE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateFridgeItem(@PathVariable String name,
                                                         @PathVariable String amount,
                                                         @PathVariable Date date,
                                                         @RequestBody FridgeItemDto fridgeItemDto,
                                                         @CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return fridgeItemService.updateFridgeItem(name, amount, date, fridgeItemDto, token);
    }
}
