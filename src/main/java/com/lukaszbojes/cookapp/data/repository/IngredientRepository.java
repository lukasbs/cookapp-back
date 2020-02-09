package com.lukaszbojes.cookapp.data.repository;

import com.lukaszbojes.cookapp.data.entity.Ingredient;
import com.lukaszbojes.cookapp.data.entity.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    List<Ingredient> findAllByNameIgnoreCaseIsContaining(String name);
    void deleteByRecipe(Recipe recipe);
}
