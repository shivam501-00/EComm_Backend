package com.example.ecommerce.Controller;

import com.example.ecommerce.Configuration.JwtService;

import com.example.ecommerce.Configuration.SecurityConfig;
import com.example.ecommerce.DTO.AuthResponse;
import com.example.ecommerce.DTO.LoginRequest;
import com.example.ecommerce.DTO.RegisterRequest;
import com.example.ecommerce.Entity.Users;
import com.example.ecommerce.Service.CustomUserDetailsService;
import com.example.ecommerce.Service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
//@RequiredArgsConstructor
public class AuthController {

    private final CustomUserDetailsService userDetailService;
    private final AuthenticationManager authManager;
    @Autowired
    public AuthController(@Lazy CustomUserDetailsService userDetailService,@Lazy AuthenticationManager authManager,@Lazy UserServices userServices) {
        this.userDetailService = userDetailService;
        this.authManager = authManager;
        this.userServices = userServices;
    }

    private final UserServices userServices;


    @Autowired
    private  JwtService jwtService;


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request){
        userDetailService.registerUser(request);
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        //Authenticate user
    try{
            Authentication authentication =
                    authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

            if(authentication.isAuthenticated()){
                String token =
                        jwtService.generateToken(request.getUsername());
                return new ResponseEntity<>(token,HttpStatus.OK);
            }
            return new ResponseEntity<>("Token not created ",HttpStatus.CONFLICT);
        }
    catch (Exception e) {
        System.out.println("Authentication error: " + e.getMessage());
        return new ResponseEntity<>("Login failed: " + e.getMessage(), HttpStatus.FORBIDDEN);

    }
    }
}
