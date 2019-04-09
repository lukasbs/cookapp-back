package com.lukaszbojes.cookapp.data.repository;

import com.lukaszbojes.cookapp.data.entity.FridgeItem;
import com.lukaszbojes.cookapp.data.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FridgeItemRepository extends CrudRepository<FridgeItem, Long> {
    List<FridgeItem> findAllByUser_Name(String userName);
    List<FridgeItem> findAllByUser(User user);
    List<FridgeItem> findAllByNameAndAmountAndExpirationDateAndUser(String name, String amount, Date expirationDate, User user);
    void deleteByFridgeID(long id);
}
