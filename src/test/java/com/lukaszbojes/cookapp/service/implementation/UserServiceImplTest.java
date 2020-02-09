package com.lukaszbojes.cookapp.service.implementation;

import com.lukaszbojes.cookapp.data.dto.UserDto;
import com.lukaszbojes.cookapp.data.entity.User;
import com.lukaszbojes.cookapp.data.repository.*;
import com.lukaszbojes.cookapp.service.UserService;
import com.lukaszbojes.cookapp.util.TokenAuthenticationFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:application.properties"})
@DataJpaTest
public class UserServiceImplTest {

    @Autowired
    Environment environment;

    @MockBean
    TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    UserRepository userRepository;
    @Autowired
    FavouriteRepository favouriteRepository;
    @Autowired
    FridgeItemRepository fridgeItemRepository;
    @Autowired
    ShoppingListItemRepository shoppingListItemRepository;

    private UserService userService;

    @Before
    public void setup() {
        this.userService = new UserServiceImpl(this.userRepository, new ModelMapper(), this.environment,
                this.favouriteRepository, this.fridgeItemRepository, this.shoppingListItemRepository);
    }

    @Test
    public void registerUser() {
    }

    @Test
    public void loginUser() {
    }

    @Test
    public void loginAdminUser() {
    }

    @Test
    public void getAllUsers() {
    }

    @Test
    public void getUserByName() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void addUser() {
    }

    @Test
    public void isValid_Unauthorized() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYWRtaW4iLCJwYXNzd29yZCI6ImFkbWluIn0.u4QIQTIV59iG_Hjl7FdnRF99AtlUxfswlTCFxwgKoYQ";

        this.userRepository.save(new User("test2", "test2", "USER"));

        assertEquals(
                this.userService.isValid(token).getStatusCode(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @Test
    public void isValid_Authorized() {
        UserDto userDto = new UserDto("test", "test", "USER");

        this.userService.addUser(userDto);
        userDto.setPassword("test");

        ResponseEntity<Object> response = this.userService.loginUser(userDto);
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);

        String token = cookies.get(0).split(";")[0].split("=")[1];

        assertEquals(
                this.userService.isValid(token).getStatusCode(),
                HttpStatus.OK
        );
    }
}
