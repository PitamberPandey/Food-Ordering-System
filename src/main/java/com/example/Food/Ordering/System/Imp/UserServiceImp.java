package com.example.Food.Ordering.System.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Food.Ordering.System.Model.User;
import com.example.Food.Ordering.System.Service.UserService;
import com.example.Food.Ordering.System.config.JwtProvider;
import com.example.Food.Ordering.System.repository.UserRepository;



@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    @Transactional
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user=findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

    User user=userRepository.findByEmail(email);
    if(user==null){
      throw new Exception("User not found");
    }
       return user;
    }
}
