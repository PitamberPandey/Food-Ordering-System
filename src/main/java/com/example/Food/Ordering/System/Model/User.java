package com.example.Food.Ordering.System.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
  @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private String fullName;
private String email;

@JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
private String password;
private User_Role role=User_Role.ROLE_CONSTOMER;
@JsonIgnore
@OneToMany(cascade =CascadeType.ALL,mappedBy = "customer")
private List<Order> orders = new ArrayList<>();

@ElementCollection
private List<RestaurabtDto> favorites=new ArrayList<>();

@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address = new ArrayList<>();
 
}
