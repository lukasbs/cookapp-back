package com.lukaszbojes.cookapp.service.implementation;

import com.lukaszbojes.cookapp.data.dto.MessageDto;
import com.lukaszbojes.cookapp.data.dto.RecipeDto;
import com.lukaszbojes.cookapp.data.entity.Favourite;
import com.lukaszbojes.cookapp.data.entity.Recipe;
import com.lukaszbojes.cookapp.data.entity.User;
import com.lukaszbojes.cookapp.data.repository.FavouriteRepository;
import com.lukaszbojes.cookapp.data.repository.RecipeRepository;
import com.lukaszbojes.cookapp.data.repository.UserRepository;
import com.lukaszbojes.cookapp.service.FavouriteService;
import com.lukaszbojes.cookapp.util.Constraints;
import com.lukaszbojes.cookapp.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private FavouriteRepository favouriteRepository;
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;
    private Environment environment;
    private ModelMapper modelMapper;

    public FavouriteServiceImpl(RecipeRepository recipeRepository,
                                UserRepository userRepository,
                                Environment environment,
                                ModelMapper modelMapper,
                                FavouriteRepository favouriteRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.environment = environment;
        this.modelMapper = modelMapper;
        this.favouriteRepository = favouriteRepository;
    }

    @Override
    public ResponseEntity<Object> getAllFavourites(String token) {
        List<Favourite> favourites = this.favouriteRepository.findAllByUser(Utils.getUserFromToken(token,this.environment, this.userRepository));
        List<Recipe> favouriteRecipes = new ArrayList<>();

        for(Favourite favourite : favourites) {
            favouriteRecipes.add(favourite.getRecipe());
        }

        List<RecipeDto> favouriteRecipeDtos = Utils.mapRecipeEntitiesToDtos(favouriteRecipes, this.modelMapper);

        return favouriteRecipeDtos.size() > 0 ? new ResponseEntity<>(favouriteRecipeDtos, HttpStatus.OK) :
                new ResponseEntity<>(new MessageDto(Constraints.RECIPE_GET_ALL_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Object> addFavourite(String name, String token) {
        User user = Utils.getUserFromToken(token, this.environment, this.userRepository);
        Recipe recipe = this.recipeRepository.findByName(name);
        if(user != null || recipe != null){
            if(this.favouriteRepository.findByUserAndRecipe(user,recipe) != null){
                return new ResponseEntity<>(new MessageDto(Constraints.FAVOURITE_ADD_ERROR_ALREADY_ADDED_MESSAGE), HttpStatus.CONFLICT);
            } else{
                this.favouriteRepository.save(new Favourite(user, recipe));
                return new ResponseEntity<>(new MessageDto(Constraints.FAVOURITE_ADD_SUCCESS_MESSAGE), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new MessageDto(Constraints.FAVOURITE_ADD_ERROR_NOT_FOUND_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> deleteFavourite(String name, String token) {
        User user = Utils.getUserFromToken(token, this.environment, this.userRepository);
        Recipe recipe = this.recipeRepository.findByName(name);
        if(user != null || recipe != null){
            this.favouriteRepository.deleteByUserAndRecipe(user, recipe);
            return new ResponseEntity<>(new MessageDto(Constraints.FAVOURITE_DELETE_SUCCESS_MESSAGE), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageDto(Constraints.FAVOURITE_DELETE_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }
}
