package com.lukaszbojes.cookapp.data.repository;

import com.lukaszbojes.cookapp.data.entity.ShoppingListItem;
import com.lukaszbojes.cookapp.data.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListItemRepository extends CrudRepository<ShoppingListItem, Long> {
    List<ShoppingListItem> findAllByUser_Name(String userName);
    List<ShoppingListItem> findAllByUser(User user);
    List<ShoppingListItem> findAllByNameAndAmountAndUser(String name, String amount, User user);
    void deleteByShoppingListID(long id);
}
