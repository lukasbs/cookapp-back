package com.lukaszbojes.cookapp.service.implementation;

import com.lukaszbojes.cookapp.data.dto.*;
import com.lukaszbojes.cookapp.data.entity.*;
import com.lukaszbojes.cookapp.data.repository.FavouriteRepository;
import com.lukaszbojes.cookapp.data.repository.FridgeItemRepository;
import com.lukaszbojes.cookapp.data.repository.ShoppingListItemRepository;
import com.lukaszbojes.cookapp.data.repository.UserRepository;
import com.lukaszbojes.cookapp.service.UserService;
import com.lukaszbojes.cookapp.util.Constants;
import com.lukaszbojes.cookapp.util.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Environment environment;
    private final FavouriteRepository favouriteRepository;
    private final FridgeItemRepository fridgeItemRepository;
    private final ShoppingListItemRepository shoppingListItemRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, Environment environment,
                           FavouriteRepository favouriteRepository, FridgeItemRepository fridgeItemRepository,
                           ShoppingListItemRepository shoppingListItemRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.environment = environment;
        this.favouriteRepository = favouriteRepository;
        this.fridgeItemRepository = fridgeItemRepository;
        this.shoppingListItemRepository = shoppingListItemRepository;
    }

    @Override
    public ResponseEntity<Object> registerUser(UserDto userDto) {
        if(userDto.getName() != null && userDto.getPassword() != null) {
            if(userRepository.findByName(userDto.getName()) != null){
                return new ResponseEntity<>(new MessageDto(Constants.ALREADY_REGISTERED_MESSAGE), HttpStatus.CONFLICT);
            } else {
                String sha256hex = DigestUtils.sha256Hex(userDto.getPassword());
                userRepository.save(new User(userDto.getName(), sha256hex, Constants.ROLE_USER));
                return new ResponseEntity<>(new MessageDto(Constants.REGISTERED_SUCCESS_MESSAGE), HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>(new MessageDto(Constants.EMPTY_CREDENTIALS_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> loginUser(UserDto userDto) {
        if(userDto.getName() != null && userDto.getPassword() != null) {
            String sha256hex = DigestUtils.sha256Hex(userDto.getPassword());
            if(userRepository.findByNameAndPassword(userDto.getName(), sha256hex) != null){
                HttpHeaders httpHeaders = this.getCookieHeaders(userDto);
                return new ResponseEntity<>(
                        new LoginDto(
                                getToken(userDto.getName()),
                                getFavouriteRecipes(userDto.getName()),
                                getFridgeItems(userDto.getName()),
                                getShoppingListItems(userDto.getName())

                        ),
                        httpHeaders,
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(new MessageDto(Constants.LOGIN_FAILED_MESSAGE), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(new MessageDto(Constants.EMPTY_CREDENTIALS_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> loginAdminUser(UserDto userDto) {
        if(userDto.getName() != null && userDto.getPassword() != null) {
            String sha256hex = DigestUtils.sha256Hex(userDto.getPassword());
            User user = userRepository.findByNameAndPassword(userDto.getName(), sha256hex);
            if(user != null && user.getRole().equals(Constants.ROLE_ADMIN)){
                HttpHeaders httpHeaders = this.getCookieHeaders(userDto);
                return new ResponseEntity<>(
                        new LoginDto(
                                getToken(userDto.getName()),
                                getFavouriteRecipes(userDto.getName()),
                                getFridgeItems(userDto.getName()),
                                getShoppingListItems(userDto.getName())
                        ),
                        httpHeaders,
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(new MessageDto(Constants.LOGIN_FAILED_MESSAGE), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(new MessageDto(Constants.EMPTY_CREDENTIALS_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> getAllUsers() {
        ArrayList<UserDto> userDtos = Utils.mapUserEntitiesToDtos(this.userRepository.findAll(), this.modelMapper);
        return userDtos.size() > 0 ? new ResponseEntity<>(userDtos, HttpStatus.OK) :
                new ResponseEntity<>(new MessageDto(Constants.USER_GET_ALL_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
    }

//    @Override
//    public ResponseEntity<Object> getUserByName(String name) {
//        if(this.userRepository.findByName(name) != null) {
//            return new ResponseEntity<>(this.userRepository.findByName(name), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new MessageDto(Constants.USER_GET_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
//    }

    @Override
    public ResponseEntity<Object> getUserByName(String name) {
        ArrayList<UserDto> userDtos = Utils.mapUserEntitiesToDtos(this.userRepository.findAllByNameStartingWith(name), this.modelMapper);
        return userDtos.size() > 0 ? new ResponseEntity<>(userDtos, HttpStatus.OK) :
                new ResponseEntity<>(new MessageDto(Constants.USER_GET_ERROR_MESSAGE), HttpStatus.NO_CONTENT);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> deleteUser(String name) {
        if(this.userRepository.findByName(name) != null) {
            this.userRepository.deleteByName(name);
            return new ResponseEntity<>(new MessageDto(Constants.USER_DELETE_SUCCESS_MESSAGE), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDto(Constants.USER_DELETE_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> updateUser(String name, UserDto userDto) {
        if(this.userRepository.findByName(userDto.getName()) != null && !userDto.getName().equals(name)) {
            return new ResponseEntity<>(new MessageDto(Constants.USER_ADD_ERROR_ALREADY_ADDED_MESSAGE), HttpStatus.CONFLICT);
        }
        User user = this.userRepository.findByName(name);
        if(user != null) {
            user.setName(userDto.getName());
            user.setPassword(DigestUtils.sha256Hex(userDto.getPassword()));
            user.setRole(userDto.getRole());

            this.userRepository.save(user);

            return new ResponseEntity<>(new MessageDto(Constants.USER_UPDATE_SUCCESS_MESSAGE), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDto(Constants.USER_UPDATE_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> addUser(UserDto userDto) {
        if(this.userRepository.findByName(userDto.getName()) != null) {
            return new ResponseEntity<>(new MessageDto(Constants.USER_ADD_ERROR_ALREADY_ADDED_MESSAGE), HttpStatus.CONFLICT);
        }
        if(userDto.getName() != null && userDto.getPassword() != null && userDto.getRole() != null){
            String sha256hex = DigestUtils.sha256Hex(userDto.getPassword());
            userDto.setPassword(sha256hex);
            this.userRepository.save(this.modelMapper.map(userDto, User.class));
            return new ResponseEntity<>(new MessageDto(Constants.USER_ADD_SUCCESS_MESSAGE), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new MessageDto(Constants.ERROR_MISSING_FIELDS_MESSAGE), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<Object> isValid(String token) {
        Jws<Claims> claims = Jwts.parser()
            .setSigningKey(TextCodec.BASE64.decode(environment.getProperty(Constants.SECRET_KEY_PROPERTY)))
            .parseClaimsJws(token);
        String name = (String)claims.getBody().get(Constants.FIELD_NAME);

        User user = this.userRepository.findByName(name);

        return user != null ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Object> logout() {
        return new ResponseEntity<>(
                new MessageDto(Constants.USER_LOGOUT_MESSAGE),
                this.clearCookieHeader(),
                HttpStatus.OK
        );
    }

    private List<RecipeDto> getFavouriteRecipes(String userName) {
        List<Favourite> favourites = this.favouriteRepository.findAllByUser_Name(userName);
        List<Recipe> favouriteRecipes = new ArrayList<>();

        for(Favourite favourite : favourites) {
            favouriteRecipes.add(favourite.getRecipe());
        }

        return Utils.mapRecipeEntitiesToDtos(favouriteRecipes, this.modelMapper);
    }

    private List<FridgeItemDto> getFridgeItems(String userName) {
        return Utils.mapFridgeItemEntitiesToDtos(this.fridgeItemRepository.findAllByUser_Name(userName), this.modelMapper);
    }

    private List<ShoppingListItemDto> getShoppingListItems(String userName) {
        return Utils.mapShoppingListItemEntitiesToDtos(this.shoppingListItemRepository.findAllByUser_Name(userName), this.modelMapper);
    }

    private String getToken(String name) {
        return Jwts.builder()
                .claim(Constants.FIELD_NAME, name)
                .setExpiration(new Date(new Date().getTime() + 3600000))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(environment.getProperty(Constants.SECRET_KEY_PROPERTY))
                )
                .compact();
    }

    private HttpHeaders getCookieHeaders(UserDto userDto) {
        Date expirationDate = Date.from(ZonedDateTime.now().toInstant());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, Constants.TOKEN_COOKIE_NAME_SETUP_PREFIX +
                getToken(userDto.getName()) + Constants.TOKEN_COOKIE_NAME_SETUP_SUFIX + expirationDate.toString());
        return httpHeaders;
    }

    private HttpHeaders clearCookieHeader() {
        Date expirationDate = Date.from(ZonedDateTime.now().minusDays(1).toInstant());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, Constants.TOKEN_COOKIE_NAME_SETUP_PREFIX +
                Constants.TOKEN_COOKIE_NAME_SETUP_SUFIX + expirationDate.toString());
        return httpHeaders;
    }
}
