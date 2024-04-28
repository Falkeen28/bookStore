package com.bookStore.bookStore.service;

import com.bookStore.bookStore.entity.MyUser;
import com.bookStore.bookStore.model.MyUserDTO;
import com.bookStore.bookStore.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserService {
    @Autowired
    private MyUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public MyUserService(MyUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void register(MyUserDTO myUserDTO) {
        MyUser myUser = new MyUser();
        myUser.setUsername(myUserDTO.getUsername());
        myUser.setPassword(passwordEncoder.encode(myUserDTO.getPassword()));
        myUser.setRole("USER");
        userRepository.save(myUser);
    }

    public MyUser findByUsername(String login) {
        return userRepository.findByUsername(login);
    }

}


