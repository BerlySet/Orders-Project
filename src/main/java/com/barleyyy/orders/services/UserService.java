package com.barleyyy.orders.services;

import com.barleyyy.orders.dto.UpdateProfileUserData;
import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private static final Logger log = LogManager.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Call findByEmail(email) to check email already exists or not");
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("user with email '%s' not found", email)
        ));
    }

    public User registerUser(User user) {
        log.info("Call findByEmail(email) to check email already exists or not");
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if(userExists) {
            throw new RuntimeException(
                    String.format("User with email '%s' already exists", user.getEmail())
            );
        }
        log.info("Call bCryptPasswordEncoder.encode(password) to encode password");
        String hashPassword = bCryptPasswordEncoder.encode(user.getPassword());
        log.info("Call user.setPassword(hashPassword) to set password");
        user.setPassword(hashPassword);
        log.info("Call save(user) to save user data");
        return userRepository.save(user);
    }

    public List<User> getALlUsers() {
        log.info("Call findAll() to get all users");
        return userRepository.findAll();
    }

    public User getUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Call authentication.getPrincipal() to get authenticated user");
        return (User) authentication.getPrincipal();
    }

    public User updateProfile(UpdateProfileUserData newUserData) {
        log.info("Call getUserLoggedIn() to get authenticated user");
        User userLoggedIn = getUserLoggedIn();
        userLoggedIn.setFullName(newUserData.getFullName());
        userLoggedIn.setDateOfBirth(newUserData.getDateOfBirth());
        userLoggedIn.setGender(newUserData.getGender());
        log.info("Call setter to set fullName, dateOfBirth, and Gender for user");
        log.info("Call save() to save new user data");
        return userRepository.save(userLoggedIn);
    }
}
