package com.anums.blogApp.DiscussPoint.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    UserModel findByUsername(String username);
}
