package com.lukaszbojes.cookapp.controller;

import com.lukaszbojes.cookapp.data.dto.UserDto;
import com.lukaszbojes.cookapp.service.UserService;
import com.lukaszbojes.cookapp.util.Constants;
import com.lukaszbojes.cookapp.util.Utils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constants.BASE_USER_URL)
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = Constants.REGISTER_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @PostMapping(path = Constants.LOGIN_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUser(@RequestBody UserDto userDto) {
        return userService.loginUser(userDto);
    }

    @PostMapping(path = Constants.ADMIN_LOGIN_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginAdminUser(@RequestBody UserDto userDto) {
        return userService.loginAdminUser(userDto);
    }

    @GetMapping(path = Constants.ADMIN_GET_ALL_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = Constants.ADMIN_GET_BY_NAME_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @DeleteMapping(path = Constants.ADMIN_DELETE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(@PathVariable String name) {
        return userService.deleteUser(name);
    }

    @PutMapping(path = Constants.ADMIN_UPDATE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUser(@PathVariable String name, @RequestBody UserDto userDto) {
        return userService.updateUser(name, userDto);
    }

    @PostMapping(path = Constants.ADMIN_ADD_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PostMapping(path = Constants.IS_VALID_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> isValid(@CookieValue(Constants.TOKEN_COOKIE_NAME) String token) {
        return userService.isValid(token);
    }

    @PostMapping(path = Constants.LOGOUT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> logout() {
        return userService.logout();
    }

    @PostMapping(path = Constants.CLIENT_CHECK, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> checkClient() {
        return Utils.checkClient();
    }
}
