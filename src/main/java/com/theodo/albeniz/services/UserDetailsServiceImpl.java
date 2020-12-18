package com.theodo.albeniz.services;

import com.theodo.albeniz.database.entities.UserEntity;
import com.theodo.albeniz.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("MyUserDetails")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findByUsername(s);
        if(user.isPresent()){
            return user.get();
        }else {
            throw new UsernameNotFoundException(("User not found"));
        }
    }
}
