package com.anums.blogApp.DiscussPoint.users.services;

import com.anums.blogApp.DiscussPoint.users.UserModel;

public interface UserService {
    void save(UserModel user);

    UserModel findByUsername(String username);
}
