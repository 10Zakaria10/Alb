package com.theodo.albeniz.services;

import com.theodo.albeniz.database.entities.UserEntity;
import com.theodo.albeniz.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserSignUpService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserEntity saveUser(UserEntity userEntity){
        try {
            userEntity.setId(UUID.randomUUID());
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            return userRepository.save(userEntity);
        }catch (Exception e){
            return null;
        }
    }

}
