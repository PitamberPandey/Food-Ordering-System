package com.example.Food.Ordering.System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Food.Ordering.System.Model.RestaurabtDto;
import com.example.Food.Ordering.System.Model.Restaurant;
import com.example.Food.Ordering.System.Model.User;
import com.example.Food.Ordering.System.Service.RestaurantService;
import com.example.Food.Ordering.System.Service.UserService;

@RestController
@RequestMapping("api/restaurants")
public class RestaurantController {

    @Autowired
    private UserService userService;
  
    @Autowired
    private RestaurantService restaurantService;

    // Search restaurants based on a key
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
         @RequestParam String key,
         @RequestHeader("Authorization") String jwt) throws Exception {

        // Fetch the user based on JWT token
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.searchRestaurants(key);

        // Return the list of restaurants with HTTP OK status
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    // Get all restaurants
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
        @RequestHeader("Authorization") String jwt) throws Exception {

        // Fetch the user based on JWT token
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();

        // Return the list of restaurants with HTTP OK status
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    // Get restaurant details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Long id) throws Exception {

        // Fetch the user based on JWT token
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);

        // Return the restaurant details with HTTP OK status
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    // Add restaurant to favorites
    @GetMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurabtDto> addToFavorites(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Long id) throws Exception {

        // Fetch the user based on JWT token
        User user = userService.findUserByJwtToken(jwt);
        RestaurabtDto restaurantDto = restaurantService.addToFavoritesList(id, user);

        // Return the updated favorite restaurant details with HTTP OK status
        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }
}
