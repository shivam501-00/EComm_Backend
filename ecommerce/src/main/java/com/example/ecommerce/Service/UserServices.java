package com.example.ecommerce.Service;

import com.example.ecommerce.Configuration.JwtService;
import com.example.ecommerce.DTO.LoginRequest;
import com.example.ecommerce.Entity.Users;
import com.example.ecommerce.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();




    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public Users updateUser(Long id, Users updatedUser) {
     return userRepo.findById(id).map(user -> {
         user.setUserName(updatedUser.getUsername());
         user.setEmail(updatedUser.getEmail());
         user.setPassword(updatedUser.getPassword());
         user.setRole(updatedUser.getRole());

            return userRepo.save(user);
     }).orElseThrow(()-> {
         return new RuntimeException("User not found");
     });

    }

    public Users addUser(Users user) {
        if(userRepo.getByEmail(user.getEmail())){
            throw new RuntimeException("User already Present in application");
        }
        return userRepo.save(user);
    }

    public void deleteUserById(Long id) {
//    Optional<Users> user = getUserById(id);
    userRepo.deleteById(id);
    }
}
