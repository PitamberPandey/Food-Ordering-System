package com.example.Food.Ordering.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Food.Ordering.System.Model.User;

public interface  UserRepository extends JpaRepository<User,Long> {

  public User findByEmail(String username);



}
