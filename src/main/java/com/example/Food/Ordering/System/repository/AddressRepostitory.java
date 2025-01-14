package com.example.Food.Ordering.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Food.Ordering.System.Model.Address;

public interface AddressRepostitory extends JpaRepository<Address,Long> {

}
