package com.nagp.products.service;

import com.nagp.products.dao.UserRepository;
import com.nagp.products.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void syncUserData(User user){
        userRepository.save(user);
        //return user;
    }

    public User getUser(String userId){
        Optional<User> res = userRepository.findById(userId);
        return res.orElseThrow(() -> new IllegalArgumentException("Value is not present"));
    }


}
