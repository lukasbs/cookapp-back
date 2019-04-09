package com.lukaszbojes.cookapp.data.repository;

import com.lukaszbojes.cookapp.data.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findByName(String name);
    void deleteByName(String name);
    List<Recipe> findAllByNameStartingWith(String name);
    List<Recipe> findAllByNameIgnoreCaseIsContaining(String name);

}
