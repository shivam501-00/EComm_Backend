package com.example.ecommerce.Service;

import com.example.ecommerce.Entity.Users;
import com.example.ecommerce.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));

        /* Now once we find our user using email, now we need to return userdetails object from this method so that AuthenticationProvider can use it.
            and for that what we did is just called constructor of User obj inside userDetails package*/
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("Role_"+ user.getRole()))
        );
    }
}
