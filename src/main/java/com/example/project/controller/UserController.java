package com.example.project.controller;

import com.example.project.models.User;
import com.example.project.utilities.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    UserService userService;
    @Autowired
    public UserController(UserService userService) {this.userService = userService;}
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody User user
    ){
        log.info("Called register of user:" + user.getLogin());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
    }
}
