package com.example.Food.Ordering.System.Service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Food.Ordering.System.Model.User;
import com.example.Food.Ordering.System.Model.User_Role;
import com.example.Food.Ordering.System.repository.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user=userRepository.findByEmail(username);
    if(user==null){
      throw new UsernameNotFoundException("User not forund with email"+username);
    }
    User_Role role=user.getRole();
    
    List<GrantedAuthority> authorities=new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role.toString()));

    return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
  }

  



}
