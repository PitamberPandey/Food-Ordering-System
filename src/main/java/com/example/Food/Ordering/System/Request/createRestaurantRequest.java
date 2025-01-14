package com.example.Food.Ordering.System.Request;

import java.util.List;

import com.example.Food.Ordering.System.Model.Address;
import com.example.Food.Ordering.System.Model.ContactInformation;

import lombok.Data;

@Data

public class createRestaurantRequest {

  private Long id;
  private String name;
  private String description;
  private String cusineType;
  private Address address;
  private ContactInformation contactInformation;
  private String openingHours;
  private List<String> images;
  



}
