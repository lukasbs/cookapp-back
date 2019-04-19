package com.lukaszbojes.cookapp.service.implementation;

import com.lukaszbojes.cookapp.data.dto.MessageDto;
import com.lukaszbojes.cookapp.data.dto.ShoppingListItemDto;
import com.lukaszbojes.cookapp.data.entity.ShoppingListItem;
import com.lukaszbojes.cookapp.data.repository.ShoppingListItemRepository;
import com.lukaszbojes.cookapp.data.repository.UserRepository;
import com.lukaszbojes.cookapp.service.ShoppingListItemService;
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
public class ShoppingListItemServiceImpl implements ShoppingListItemService {

    private ShoppingListItemRepository shoppingListItemRepository;
    private UserRepository userRepository;
    private Environment environment;
    private ModelMapper modelMapper;

    public ShoppingListItemServiceImpl(ShoppingListItemRepository shoppingListItemRepository,
                                       UserRepository userRepository,
                                       Environment environment,
                                       ModelMapper modelMapper) {
        this.shoppingListItemRepository = shoppingListItemRepository;
        this.userRepository = userRepository;
        this.environment = environment;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Object> getAllShoppingListItems(String token) {
        ArrayList<ShoppingListItemDto> shoppingListItemDtos = Utils.mapShoppingListItemEntitiesToDtos(
                this.shoppingListItemRepository.findAllByUser(Utils.getUserFromToken(token, this.environment, this.userRepository)), this.modelMapper);
        return shoppingListItemDtos.size() > 0 ? new ResponseEntity<>(shoppingListItemDtos, HttpStatus.OK) :
                new ResponseEntity<>(new MessageDto(Constants.SHOPPING_GET_ALL_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Object> addShoppingListItem(ShoppingListItemDto shoppingListItemDto, String token) {
        if(shoppingListItemDto.getName() != null && shoppingListItemDto.getAmount() != null){
            ShoppingListItem shoppingListItem = this.modelMapper.map(shoppingListItemDto, ShoppingListItem.class);
            shoppingListItem.setUser(Utils.getUserFromToken(token, this.environment, this.userRepository));
            this.shoppingListItemRepository.save(shoppingListItem);
            return new ResponseEntity<>(new MessageDto(Constants.SHOPPING_ADD_SUCCESS_MESSAGE), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new MessageDto(Constants.ERROR_MISSING_FIELDS_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> deleteShoppingListItem(ShoppingListItemDto shoppingListItemDto, String token) {
        List<ShoppingListItem> items = this.shoppingListItemRepository.findAllByNameAndAmountAndUser(
                shoppingListItemDto.getName(),
                shoppingListItemDto.getAmount(),
                Utils.getUserFromToken(token, this.environment, this.userRepository));
        if(items.size() > 0){
            this.shoppingListItemRepository.deleteByShoppingListID(items.get(0).getShoppingListID());
            return new ResponseEntity<>(new MessageDto(Constants.SHOPPING_DELETE_SUCCESS_MESSAGE), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDto(Constants.SHOPPING_DELETE_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> updateShoppingListItem(String name, String amount, ShoppingListItemDto shoppingListItemDto, String token) {
        if(name != null && shoppingListItemDto.getName() != null && shoppingListItemDto.getAmount() != null){
            List<ShoppingListItem> items = this.shoppingListItemRepository.findAllByNameAndAmountAndUser(
                    name, amount, Utils.getUserFromToken(token, this.environment, this.userRepository));
            if(items.size() > 0){
                items.get(0).setName(shoppingListItemDto.getName());
                items.get(0).setAmount(shoppingListItemDto.getAmount());

                this.shoppingListItemRepository.save(items.get(0));

                return new ResponseEntity<>(new MessageDto(Constants.SHOPPING_UPDATE_SUCCESS_MESSAGE), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageDto(Constants.SHOPPING_UPDATE_ERROR_MESSAGE), HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(new MessageDto(Constants.ERROR_MISSING_FIELDS_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }
}
