package com.lukaszbojes.cookapp.controller;

import com.lukaszbojes.cookapp.data.dto.UserDto;
import com.lukaszbojes.cookapp.service.UserService;
import com.lukaszbojes.cookapp.util.Constraints;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constraints.BASE_USER_URL)
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = Constraints.REGISTER_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @PostMapping(path = Constraints.LOGIN_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUser(@RequestBody UserDto userDto) {
        return userService.loginUser(userDto);
    }

    @PostMapping(path = Constraints.ADMIN_LOGIN_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginAdminUser(@RequestBody UserDto userDto) {
        return userService.loginAdminUser(userDto);
    }

    @GetMapping(path = Constraints.ADMIN_GET_ALL_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = Constraints.ADMIN_GET_BY_NAME_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @DeleteMapping(path = Constraints.ADMIN_DELETE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(@PathVariable String name) {
        return userService.deleteUser(name);
    }

    @PutMapping(path = Constraints.ADMIN_UPDATE_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUser(@PathVariable String name, @RequestBody UserDto userDto) {
        return userService.updateUser(name, userDto);
    }

    @PostMapping(path = Constraints.ADMIN_ADD_URL , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }
}
