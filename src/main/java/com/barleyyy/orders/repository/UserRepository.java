package com.barleyyy.orders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.barleyyy.orders.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  List<User> findByFullName(String fullName);
}
