package com.anums.blogApp.DiscussPoint.users.services;

import java.util.HashSet;

import com.anums.blogApp.DiscussPoint.users.RoleRepository;
import com.anums.blogApp.DiscussPoint.users.UserModel;
import com.anums.blogApp.DiscussPoint.users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(UserModel user) {
        user.setPassword(user.getPassword());
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
