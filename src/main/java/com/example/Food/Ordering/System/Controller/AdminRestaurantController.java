package com.example.Food.Ordering.System.Controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Food.Ordering.System.Model.Restaurant;
import com.example.Food.Ordering.System.Model.User;
import com.example.Food.Ordering.System.Request.createRestaurantRequest;
import com.example.Food.Ordering.System.Service.RestaurantService;
import com.example.Food.Ordering.System.Service.UserService;
import com.example.Food.Ordering.System.reponse.MessageResponse;


@RestController
@RequestMapping("api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Restaurant> createRestaurant(
        @RequestBody createRestaurantRequest req, 
        @RequestHeader("Authorization") String jwt)throws Exception {


          User user=userService.findUserByJwtToken(jwt);
          Restaurant restaurant=restaurantService.createRestaurant(req, user);
          
        return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
        @RequestBody createRestaurantRequest req, 
        @RequestHeader("Authorization") String jwt,@PathVariable Long id)throws Exception {


          User user=userService.findUserByJwtToken(jwt);
          Restaurant restaurant=restaurantService.updateRestaurant(id, req);
          
        return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt, 
            @PathVariable Long id) throws Exception {

        userService.findUserByJwtToken(jwt); 
        restaurantService.deleteRestaurant(id);

        MessageResponse response = new MessageResponse();
        response.setMessage("Restaurant deleted successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt, 
            @PathVariable Long id) throws Exception {

       User user= userService.findUserByJwtToken(jwt); 
         Restaurant  restaurant=
         restaurantService.updateRestaurantStatus(id);

        
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


  @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt 
           ) throws Exception {

       User user= userService.findUserByJwtToken(jwt); 
         Restaurant  restaurant=
         restaurantService.findRestaurantById(user.getId());

        
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
