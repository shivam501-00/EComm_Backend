package com.example.ecommerce.Controller;

import com.example.ecommerce.Entity.Users;
import com.example.ecommerce.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
   private UserServices services;

    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody Users user) {
        Users newUser= null;
        try{
             newUser = services.addUser(user);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("New User added",HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> usersList= services.getAllUsers();
        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }

    //Get user by User ID
    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<Users>> getUserById(@PathVariable Long id){
        Optional <Users> user = services.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    //Update user as per id
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Users updatedUser){
        Users user1 = null;
        try{
            user1 = services.updateUser(id,updatedUser);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        if (user1 != null) {
            return new ResponseEntity<>("User Updated successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Failed to Update user", HttpStatus.BAD_REQUEST);
        }
    }
    //Delete user as per Id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        Optional<Users> user = services.getUserById(id);
        if (user!= null){
            services.deleteUserById(id);
            return new ResponseEntity<>("User Deleted Successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
    }

}
