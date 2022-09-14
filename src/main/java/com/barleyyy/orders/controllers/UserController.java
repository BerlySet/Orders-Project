package com.barleyyy.orders.controllers;

import java.util.List;

import com.barleyyy.orders.utils.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.repository.UserRepository;

@RestController
public class UserController {
  @Autowired
  UserRepository userRepository;

  @GetMapping("/users")
  public List<User> index() {
    return userRepository.findAll();
  }

  @PostMapping("/users")
  public User addUser(@RequestBody User param) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String hashPassword = bCryptPasswordEncoder.encode(param.getPassword());

    User user = new User();
    user.setFullName(param.getFullName());
    user.setEmail(param.getEmail());
    user.setGender(Gender.valueOf(param.getGender()));
    user.setPassword(hashPassword);
    user.setDateOfBirth(param.getDateOfBirth());
    user.setCreatedAt(param.getCreatedAt());
    user.setUpdatedAt(param.getUpdatedAt());
    return this.userRepository.save(user);
  }
}
