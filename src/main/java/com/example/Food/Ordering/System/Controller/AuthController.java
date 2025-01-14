package com.example.Food.Ordering.System.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Food.Ordering.System.Model.Cart;
import com.example.Food.Ordering.System.Model.User;
import com.example.Food.Ordering.System.Request.LoginRequest;
import com.example.Food.Ordering.System.Service.CustomerUserDetailsService;
import com.example.Food.Ordering.System.config.JwtProvider;
import com.example.Food.Ordering.System.reponse.AuthResponse;
import com.example.Food.Ordering.System.repository.CartRepository;
import com.example.Food.Ordering.System.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    // Signup method
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse("Email is already in use", null, null));
        }

        // Create a new user
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        newUser.setRole(user.getRole());
        newUser.setPassword(passwordEncoder.encode(user.getPassword())); // Use the actual password

        // Save user and create a cart
        User savedUser = userRepository.save(newUser);
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        // Generate JWT token
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getEmail(), null);
        String jwt = jwtProvider.generateToken(authentication);

        // Return response
        AuthResponse response = new AuthResponse("Registration successful", jwt, savedUser.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Signin method
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) {
        try {
            // Authenticate user
            Authentication authentication = authenticate(req.getEmail(), req.getPassword());

            // Generate JWT token
            String jwt = jwtProvider.generateToken(authentication);
            

            // Return response
            User user = userRepository.findByEmail(req.getEmail());
            AuthResponse response = new AuthResponse("Login successful", jwt, user.getRole());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Invalid email or password", null, null));
        }
    }

    // Authenticate user credentials
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid email");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
