package com.example.SpringSegurity.service.servisimpl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // lógica para buscar el usuario (DB, repo, etc)
        return User.builder()
                .username(username)
                .password("password")
                .roles("USER")
                .build();
    }
}