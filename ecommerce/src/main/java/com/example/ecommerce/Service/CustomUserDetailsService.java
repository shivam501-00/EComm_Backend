package com.example.ecommerce.Service;

import com.example.ecommerce.DTO.RegisterRequest;
import com.example.ecommerce.Entity.Role;
import com.example.ecommerce.Entity.Users;
import com.example.ecommerce.Repository.UserRepo;
//import jdk.internal.icu.impl.Punycode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
//@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;


    private final BCryptPasswordEncoder passwordEncoder;

    public CustomUserDetailsService(@Lazy BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));

        /* Now once we find our user using email, now we need to return userdetails object from this method so that AuthenticationProvider can use it.
            and for that what we did is just called constructor of User obj inside userDetails package*/
        return new User(
                user.getUsername(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("Role_"+ user.getRole()))
        );
    }
    //user siging up for first time, store user details
    public void registerUser(RegisterRequest request){
        if (userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("User already Exist!");
        }

        Users user = new Users(request.getName(),request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.valueOf(request.getRole()));
        userRepo.save(user);
    }
}