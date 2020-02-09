package com.lukaszbojes.cookapp.service;

import com.lukaszbojes.cookapp.data.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<Object> registerUser(UserDto userDto);
    ResponseEntity<Object> loginUser(UserDto userDto);
    ResponseEntity<Object> getAllUsers();
    ResponseEntity<Object> getUserByName(String name);
    ResponseEntity<Object> deleteUser(String name);
    ResponseEntity<Object> updateUser(String name, UserDto userDto);
    ResponseEntity<Object> addUser(UserDto userDto);
    ResponseEntity<Object> loginAdminUser(UserDto userDto);
    ResponseEntity<Object> isValid(String token);
    ResponseEntity<Object> logout();
}
