package com.lukaszbojes.cookapp.data.repository;

import com.lukaszbojes.cookapp.data.entity.Favourite;
import com.lukaszbojes.cookapp.data.entity.Recipe;
import com.lukaszbojes.cookapp.data.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends CrudRepository<Favourite,Long> {
    List<Favourite> findAllByUser_Name(String userName);
    List<Favourite> findAllByUser(User user);
    Favourite findByUserAndRecipe(User user, Recipe recipe);
    void deleteByUserAndRecipe(User user, Recipe recipe);
}
