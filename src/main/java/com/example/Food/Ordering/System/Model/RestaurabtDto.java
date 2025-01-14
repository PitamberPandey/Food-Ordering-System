package com.example.Food.Ordering.System.Model;

import java.util.List;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class RestaurabtDto {
  @Id
  
  private String title;


  @Column(length=1000)
  private List<String> images;

private String description;
private Long id;



}
