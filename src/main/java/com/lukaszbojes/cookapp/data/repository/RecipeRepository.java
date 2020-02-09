package com.lukaszbojes.cookapp.data.repository;

import com.lukaszbojes.cookapp.data.entity.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findByName(String name);
    void deleteByName(String name);
    List<Recipe> findAllByNameStartingWith(String name);
    List<Recipe> findAllByNameIgnoreCaseIsContaining(String name);

}
