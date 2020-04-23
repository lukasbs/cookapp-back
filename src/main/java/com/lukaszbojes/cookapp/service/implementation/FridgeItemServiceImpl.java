package com.lukaszbojes.cookapp.service.implementation;

import com.lukaszbojes.cookapp.data.dto.FridgeItemDto;
import com.lukaszbojes.cookapp.data.dto.MessageDto;
import com.lukaszbojes.cookapp.data.entity.FridgeItem;
import com.lukaszbojes.cookapp.data.entity.User;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
            User user = Utils.getUserFromToken(token, this.environment, this.userRepository);
            FridgeItem fridgeItemToUpdate = null;
            List<FridgeItem> foundFridgeItems = this.fridgeItemRepository.findAllByNameAndExpirationDateAndUser(
                    fridgeItemDto.getName(), fridgeItemDto.getExpirationDate(), user
            );

            if(foundFridgeItems != null) {
                fridgeItemToUpdate = this.getItemWithSameUnit(foundFridgeItems, fridgeItemDto.getAmount());
            }

            if(fridgeItemToUpdate != null) {
                fridgeItemToUpdate.setAmount(Utils.addAmounts(fridgeItemToUpdate.getAmount(), fridgeItemDto.getAmount()));
                this.fridgeItemRepository.save(fridgeItemToUpdate);
                return new ResponseEntity<>(new MessageDto(Constants.FRIDGE_UPDATE_SUCCESS_MESSAGE), HttpStatus.CREATED);
            } else {
                FridgeItem fridgeItem = this.modelMapper.map(fridgeItemDto, FridgeItem.class);
                fridgeItem.setUser(user);
                this.fridgeItemRepository.save(fridgeItem);
                return new ResponseEntity<>(new MessageDto(Constants.FRIDGE_ADD_SUCCESS_MESSAGE), HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>(new MessageDto(Constants.ERROR_MISSING_FIELDS_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public ResponseEntity<Object> updateFridgeItem(String name, String amount, Date expirationDate, FridgeItemDto fridgeItemDto, String token) {
        if(name != null && amount != null && expirationDate != null && fridgeItemDto.getName() != null &&
                fridgeItemDto.getAmount() != null && fridgeItemDto.getExpirationDate() != null){
            List<FridgeItem> items = this.fridgeItemRepository.findAllByNameAndAmountAndExpirationDateAndUser(
                    name, amount, expirationDate, Utils.getUserFromToken(token, this.environment, this.userRepository));
            if(items.size() > 0){
                FridgeItem currentFridgeItem = null;
                List<FridgeItem> currentItems = this.fridgeItemRepository.findAllByNameAndExpirationDateAndUser(
                        fridgeItemDto.getName(), fridgeItemDto.getExpirationDate(), Utils.getUserFromToken(token, this.environment, this.userRepository));

                if(currentItems.size() > 0) {
                    currentFridgeItem = this.getItemWithSameUnit(currentItems, fridgeItemDto.getAmount());
                }

                if(currentFridgeItem != null && items.get(0) != currentFridgeItem) {
                    this.fridgeItemRepository.deleteByFridgeID(items.get(0).getFridgeID());
                    currentFridgeItem.setAmount(Utils.addAmounts(currentFridgeItem.getAmount(), fridgeItemDto.getAmount()));
                    this.fridgeItemRepository.save(currentFridgeItem);
                    return new ResponseEntity<>(new MessageDto(Constants.FRIDGE_UPDATE_SUCCESS_MESSAGE), HttpStatus.OK);
                } else {
                    items.get(0).setName(fridgeItemDto.getName());
                    items.get(0).setAmount(fridgeItemDto.getAmount());
                    this.fridgeItemRepository.save(items.get(0));
                    return new ResponseEntity<>(new MessageDto(Constants.FRIDGE_UPDATE_SUCCESS_MESSAGE), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new MessageDto(Constants.FRIDGE_UPDATE_ERROR_MESSAGE), HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(new MessageDto(Constants.ERROR_MISSING_FIELDS_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    private FridgeItem getItemWithSameUnit(List<FridgeItem> foundFridgeItemsList, String amountToCheck) {
        for(FridgeItem fridgeItem: foundFridgeItemsList) {
            try {
                if(fridgeItem.getAmount().split(" ")[1].toLowerCase().trim()
                        .equals(amountToCheck.split(" ")[1].toLowerCase().trim())) {
                    return fridgeItem;
                }
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
        return null;
    }
}
