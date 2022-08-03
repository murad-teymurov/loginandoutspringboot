package com.company.springProject.service;

import com.company.springProject.entity.User;
import com.company.springProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailChecking {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> checkEmail(User user){
        Optional<User> checkUser = userRepository.findByEmail(user.getEmail());
        return checkUser;
    }
}
