package com.dev.spfcbank.controller;

import com.dev.spfcbank.domain.user.User;
import com.dev.spfcbank.domain.user.UserDTO;
import com.dev.spfcbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO data){
        return new ResponseEntity<>(userService.createUser(data), HttpStatus.CREATED);
    }
}
