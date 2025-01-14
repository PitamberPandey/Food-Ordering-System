package com.example.Food.Ordering.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Food.Ordering.System.Model.Cart;

public interface CartRepository  extends JpaRepository<Cart,Long > {

}
