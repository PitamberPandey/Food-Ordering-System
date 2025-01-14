package com.example.Food.Ordering.System.Service;


import com.example.Food.Ordering.System.Model.User;


public interface UserService {

  public User findUserByJwtToken(String jwt) throws Exception;

  public User findUserByEmail(String email)throws Exception;

}
