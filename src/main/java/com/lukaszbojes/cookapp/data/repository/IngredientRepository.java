package com.lukaszbojes.cookapp.data.repository;

import com.lukaszbojes.cookapp.data.entity.Ingredient;
import com.lukaszbojes.cookapp.data.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    List<Ingredient> findAllByNameIgnoreCaseIsContaining(String name);
    void deleteByRecipe(Recipe recipe);
}
