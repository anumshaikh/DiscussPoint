package com.anums.blogApp.DiscussPoint.users.services;

import java.util.HashSet;
import java.util.Set;

import com.anums.blogApp.DiscussPoint.users.Role;
import com.anums.blogApp.DiscussPoint.users.UserModel;
import com.anums.blogApp.DiscussPoint.users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        UserModel user = userRepo.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
         
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
     

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);

    }

}
