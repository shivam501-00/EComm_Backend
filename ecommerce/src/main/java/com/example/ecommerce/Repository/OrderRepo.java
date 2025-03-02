package com.example.ecommerce.Repository;

import com.example.ecommerce.Entity.Order;
import com.example.ecommerce.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByUser(Users user);
}
