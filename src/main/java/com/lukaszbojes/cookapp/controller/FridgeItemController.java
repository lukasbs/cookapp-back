package com.lukaszbojes.cookapp.controller;

import com.lukaszbojes.cookapp.data.dto.FridgeItemDto;
import com.lukaszbojes.cookapp.service.FridgeItemService;
import com.lukaszbojes.cookapp.util.Constraints;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constraints.BASE_FRIDGE_URL)
public class FridgeItemController {

    private FridgeItemService fridgeItemService;

    public FridgeItemController(FridgeItemService fridgeItemService) {
        this.fridgeItemService = fridgeItemService;
    }

    @GetMapping(path = Constraints.ALL_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllFridgeItems(@RequestHeader(Constraints.AUTHORIZATION_HEADER) String token) {
        return fridgeItemService.getAllFridgeItems(token);
    }

    @PostMapping(path = Constraints.ADD_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addFridgeItem(@RequestBody FridgeItemDto fridgeItemDto, @RequestHeader(Constraints.AUTHORIZATION_HEADER) String token) {
        return fridgeItemService.addFridgeItem(fridgeItemDto, token);
    }

    @DeleteMapping(path = Constraints.DELETE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteFridgeItem(@RequestBody FridgeItemDto fridgeItemDto, @RequestHeader(Constraints.AUTHORIZATION_HEADER) String token) {
        return fridgeItemService.deleteFridgeItem(fridgeItemDto, token);
    }
}
