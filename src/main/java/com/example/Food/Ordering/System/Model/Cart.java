package com.example.Food.Ordering.System.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @OneToOne
  private User customer;

  private Long total;

  @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
  private List<CardItem> item= new ArrayList<>();

}
