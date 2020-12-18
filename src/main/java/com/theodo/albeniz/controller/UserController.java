package com.theodo.albeniz.controller;

import com.theodo.albeniz.database.entities.UserEntity;
import com.theodo.albeniz.services.UserSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserSignUpService userSignUpService;


    @PostMapping("/signup")
    public ResponseEntity signUp (@RequestBody @Valid UserEntity userEntity){
        UserEntity savedUser = userSignUpService.saveUser(userEntity);
        if(savedUser != null ){
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
