package com.example.Food.Ordering.System.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category foodCategory;

    @ElementCollection
    @CollectionTable(name = "food_images", joinColumns = @JoinColumn(name = "food_id"))
    @Column(length = 1000)
    private List<String> images = new ArrayList<>();

    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private boolean isVegetarian;

    private boolean isSeasonal;

    @ManyToMany
    private List<IngredientsItem> ingredients = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();
}
