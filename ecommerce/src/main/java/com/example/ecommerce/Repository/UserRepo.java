package com.example.ecommerce.Repository;

import com.example.ecommerce.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
    boolean getByEmail(String email);
}
