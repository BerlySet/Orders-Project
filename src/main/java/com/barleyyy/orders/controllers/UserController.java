package com.barleyyy.orders.controllers;

import com.barleyyy.orders.dto.ResponseData;
import com.barleyyy.orders.dto.UpdateProfileUserData;
import com.barleyyy.orders.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.barleyyy.orders.entities.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("/users")
  public ResponseEntity<ResponseData<List<User>>> index() {
    ResponseData<List<User>> responseData = new ResponseData<>();
    responseData.setPayload(userService.getALlUsers());
    responseData.setStatus(true);
    responseData.getMessages().add("Get All Users Success!");
    return ResponseEntity.ok(responseData);
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseData<User>> register(@Valid @RequestBody User user){
    ResponseData<User> responseData = new ResponseData<>();
    responseData.setPayload(userService.registerUser(user));
    responseData.setStatus(true);
    responseData.getMessages().add("Register Success!");
    return ResponseEntity.ok(responseData);
  }

  @GetMapping("/detail")
  public ResponseEntity<ResponseData<User>> getUserLoggedIn() {
    ResponseData<User> responseData = new ResponseData<>();
    responseData.setPayload(userService.getUserLoggedIn());
    responseData.setStatus(true);
    responseData.getMessages().add("Here's your login details");
    return ResponseEntity.ok(responseData);
  }

  @PutMapping("/detail/update")
  public ResponseEntity<ResponseData<User>> updateProfile(@Valid @RequestBody UpdateProfileUserData userData) {
    ResponseData<User> responseData = new ResponseData<>();
    responseData.setPayload(userService.updateProfile(userData));
    responseData.setStatus(true);
    responseData.getMessages().add("Update Profile Success!");
    return ResponseEntity.ok(responseData);
  }
}
