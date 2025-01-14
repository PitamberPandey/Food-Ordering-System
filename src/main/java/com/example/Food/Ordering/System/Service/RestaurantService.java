package com.example.Food.Ordering.System.Service;

import java.util.List;


import com.example.Food.Ordering.System.Model.RestaurabtDto;
import com.example.Food.Ordering.System.Model.Restaurant;
import com.example.Food.Ordering.System.Model.User;
import com.example.Food.Ordering.System.Request.createRestaurantRequest;

public interface RestaurantService {

  public Restaurant createRestaurant(createRestaurantRequest  req,User user);

  public Restaurant updateRestaurant(Long restaurantId,createRestaurantRequest updateRestaurant)throws Exception;

  public void deleteRestaurant (Long restaurantId)throws Exception;
  public List<Restaurant> getAllRestaurant();
  public List<Restaurant>searchRestaurants(String key);

  public Restaurant findRestaurantById(Long id) throws Exception;

  public Restaurant getRestaurantByUserId(Long userId)throws Exception;

  public RestaurabtDto addToFavoritesList(Long restaurantId,User user)throws Exception;

  public Restaurant updateRestaurantStatus(Long id)throws Exception;


}
