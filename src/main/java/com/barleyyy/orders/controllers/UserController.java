package com.barleyyy.orders.controllers;

import com.barleyyy.orders.dto.ResponseData;
import com.barleyyy.orders.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    responseData.getMessages().add("Get Data Success!");
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
}
