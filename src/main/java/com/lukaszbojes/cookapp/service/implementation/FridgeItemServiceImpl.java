package com.lukaszbojes.cookapp.service.implementation;

import com.lukaszbojes.cookapp.data.dto.FridgeItemDto;
import com.lukaszbojes.cookapp.data.dto.MessageDto;
import com.lukaszbojes.cookapp.data.entity.FridgeItem;
import com.lukaszbojes.cookapp.data.repository.FridgeItemRepository;
import com.lukaszbojes.cookapp.data.repository.UserRepository;
import com.lukaszbojes.cookapp.service.FridgeItemService;
import com.lukaszbojes.cookapp.util.Constants;
import com.lukaszbojes.cookapp.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FridgeItemServiceImpl implements FridgeItemService {

    private FridgeItemRepository fridgeItemRepository;
    private UserRepository userRepository;
    private Environment environment;
    private ModelMapper modelMapper;

    public FridgeItemServiceImpl(FridgeItemRepository fridgeItemRepository, UserRepository userRepository, Environment environment, ModelMapper modelMapper) {
        this.fridgeItemRepository = fridgeItemRepository;
        this.userRepository = userRepository;
        this.environment = environment;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Object> getAllFridgeItems(String token) {
        ArrayList<FridgeItemDto> fridgeItemDtos = Utils.mapFridgeItemEntitiesToDtos(
                this.fridgeItemRepository.findAllByUser(Utils.getUserFromToken(token, this.environment, this.userRepository)), this.modelMapper);
        return fridgeItemDtos.size() > 0 ? new ResponseEntity<>(fridgeItemDtos, HttpStatus.OK) :
                new ResponseEntity<>(new MessageDto(Constants.FRIDGE_GET_ALL_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Object> addFridgeItem(FridgeItemDto fridgeItemDto, String token) {
        if(fridgeItemDto.getName() != null && fridgeItemDto.getAmount() != null && fridgeItemDto.getExpirationDate() != null) {
            FridgeItem fridgeItem = this.modelMapper.map(fridgeItemDto, FridgeItem.class);
            fridgeItem.setUser(Utils.getUserFromToken(token, this.environment, this.userRepository));
            this.fridgeItemRepository.save(fridgeItem);
            return new ResponseEntity<>(new MessageDto(Constants.FRIDGE_ADD_SUCCESS_MESSAGE), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new MessageDto(Constants.ERROR_MISSING_FIELDS_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> deleteFridgeItem(FridgeItemDto fridgeItemDto, String token) {
        List<FridgeItem> items = this.fridgeItemRepository.findAllByNameAndAmountAndExpirationDateAndUser(
                fridgeItemDto.getName(),
                fridgeItemDto.getAmount(),
                fridgeItemDto.getExpirationDate(),
                Utils.getUserFromToken(token, this.environment, this.userRepository));
        if(items.size() > 0){
            this.fridgeItemRepository.deleteByFridgeID(items.get(0).getFridgeID());
            return new ResponseEntity<>(new MessageDto(Constants.FRIDGE_DELETE_SUCCESS_MESSAGE), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDto(Constants.FRIDGE_DELETE_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
    }
}
