package com.lukaszbojes.cookapp.service;

import com.lukaszbojes.cookapp.data.dto.FridgeItemDto;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface FridgeItemService {
    ResponseEntity<Object> getAllFridgeItems(String token);
    ResponseEntity<Object> addFridgeItem(FridgeItemDto fridgeItemDto, String token);
    ResponseEntity<Object> deleteFridgeItem(FridgeItemDto fridgeItemDto, String token);
    ResponseEntity<Object> updateFridgeItem(String name, String amount, Date expirationDate, FridgeItemDto fridgeItemDto, String token);
}
