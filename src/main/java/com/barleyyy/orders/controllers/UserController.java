package com.barleyyy.orders.controllers;

import com.barleyyy.orders.dto.ResponseData;
import com.barleyyy.orders.dto.UpdateProfileUserData;
import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
  private static final Logger log = LogManager.getLogger(UserController.class);
  @Autowired
  private UserService userService;

  @GetMapping("/users")
  public ResponseEntity<ResponseData<List<User>>> index() {
    ResponseData<List<User>> responseData = new ResponseData<>();
    responseData.setPayload(userService.getALlUsers());
    responseData.setStatus(true);
    responseData.getMessages().add("Get All Users Success!");
    log.info("Return response body (Get All Users)");
    log.info(responseData.toString());
    return ResponseEntity.ok(responseData);
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseData<User>> register(@Valid @RequestBody User user){
    ResponseData<User> responseData = new ResponseData<>();
    try {
      User payload = userService.registerUser(user);
      responseData.setPayload(payload);
      responseData.setStatus(true);
      responseData.getMessages().add("Register Success!");
      log.info("Return response body (New User Successfully Register)");
      log.info(responseData.toString());
      return ResponseEntity.ok(responseData);
    } catch (RuntimeException exception) {
      responseData.setStatus(false);
      responseData.getMessages().add(exception.getMessage());
      log.error("Register failed because email" + user.getEmail() + " already taken");
      return ResponseEntity.status(HttpStatus.CONFLICT).body(responseData);
    }
  }

  @GetMapping("/detail")
  public ResponseEntity<ResponseData<User>> getUserLoggedIn() {
    ResponseData<User> responseData = new ResponseData<>();
    responseData.setPayload(userService.getUserLoggedIn());
    responseData.setStatus(true);
    responseData.getMessages().add("Here's your login details");
    log.info("Return response body (Get User Logged In Details)");
    log.info(responseData.toString());
    return ResponseEntity.ok(responseData);
  }

  @PutMapping("/detail/update")
  public ResponseEntity<ResponseData<User>> updateProfile(@Valid @RequestBody UpdateProfileUserData userData) {
    ResponseData<User> responseData = new ResponseData<>();
    responseData.setPayload(userService.updateProfile(userData));
    responseData.setStatus(true);
    responseData.getMessages().add("Update Profile Success!");
    log.info("Return response body (Update Profile User Success)");
    log.info(responseData.toString());
    return ResponseEntity.ok(responseData);
  }
}
