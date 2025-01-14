package com.example.Food.Ordering.System.Imp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Food.Ordering.System.Model.Address;
import com.example.Food.Ordering.System.Model.RestaurabtDto;
import com.example.Food.Ordering.System.Model.Restaurant;
import com.example.Food.Ordering.System.Model.User;
import com.example.Food.Ordering.System.Request.createRestaurantRequest;
import com.example.Food.Ordering.System.Service.RestaurantService;

import com.example.Food.Ordering.System.repository.AddressRepostitory;
import com.example.Food.Ordering.System.repository.RestaurantRepository;
import com.example.Food.Ordering.System.repository.UserRepository;

@Service
public class RestaurantServiceImp implements RestaurantService {

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private AddressRepostitory addressRepostitory;
  @Autowired
  private UserRepository userRepository;

  @Override
  public Restaurant createRestaurant(createRestaurantRequest req, User user) {
    Address address = addressRepostitory.save(req.getAddress());
    Restaurant restaurant = new Restaurant();
    restaurant.setAddress(address);
    restaurant.setContactInformation(req.getContactInformation());
    restaurant.setCuisineType(req.getCusineType());
    restaurant.setDescription(req.getDescription());
    restaurant.setImages(req.getImages());
    restaurant.setName(req.getName());
    restaurant.setOpeningHours(req.getOpeningHours());
    restaurant.setRegistrationDate(LocalDateTime.now());
    restaurant.setOwner(user);

    return restaurantRepository.save(restaurant);
  }

  @Override
  public Restaurant updateRestaurant(Long restaurantId, createRestaurantRequest updateRestaurant) throws Exception {
    Restaurant restaurant = findRestaurantById(restaurantId);
    if (restaurant.getCuisineType() != null) {
      restaurant.setCuisineType(updateRestaurant.getCusineType());

    }
    if (restaurant.getName() != null) {
      restaurant.setName(updateRestaurant.getName());

    }
    if (restaurant.getDescription() != null) {
      restaurant.setDescription(updateRestaurant.getDescription());

    }

    return restaurantRepository.save(restaurant);
  }

  @Override
  public void deleteRestaurant(Long restaurantId) throws Exception {

    Restaurant restaurant=findRestaurantById(restaurantId);
    restaurantRepository.delete(restaurant);
}

  @Override
  public List<Restaurant> getAllRestaurant() {
     return restaurantRepository.findAll();
  }

  @Override
  public List<Restaurant> searchRestaurants( String key) {

    return restaurantRepository.findBySearchQuery(key);
    
  }

  @Override
  public Restaurant findRestaurantById(Long id) throws Exception {
Optional<Restaurant> opt=restaurantRepository.findById(id);
if(opt.isEmpty()){
  throw new Exception("restaurant not found with id"+id);
}


    return opt.get();
  }

  @Override
  public Restaurant getRestaurantByUserId(Long userId) throws Exception {

  Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
  if(restaurant==null){
    throw new Exception("Restaurant not found with owner id"+userId);
  }
    return restaurant;
  }

  @Override
  public RestaurabtDto addToFavoritesList(Long restaurantId, User user) throws Exception {

    Restaurant restaurant=findRestaurantById(restaurantId);
    RestaurabtDto dto=new RestaurabtDto();
    dto.setDescription(restaurant.getDescription());
    dto.setImages(restaurant.getImages());
    dto.setTitle(restaurant.getName());
    dto.setId(restaurantId);
    if(user.getFavorites().contains(dto)){
      user.getFavorites().remove(dto);
    }else
      user.getFavorites().add(dto);
      userRepository.save(user);
      return dto;
    

  }

  @Override
  public Restaurant updateRestaurantStatus(Long id) throws Exception {
    Restaurant restaurant=findRestaurantById(id);
restaurant.setOpen(!restaurant.isOpen());

    return restaurantRepository.save(restaurant);
  }

}
