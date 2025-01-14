package com.example.Food.Ordering.System.Model;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor


public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
 


  // @Column(name = "street_address")
 private String streetAddress;

// @Column(name = "city")
 private String city;
  private String stateProvince;

// @Column(name = "postal_code")
 private String postalCode;

// @Column(name = "country")
 private String country;


}
