package com.sila.controller;

import com.sila.config.JwtProvider;
import com.sila.model.Card;
import com.sila.model.User;
import com.sila.repository.CartRepository;
import com.sila.repository.UserRepository;
import com.sila.response.AuthResponse;
import com.sila.service.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final CustomerUserDetailsService customerUserDetailsService;
  private final CartRepository cartRepository;
  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
    User isEmailExist = userRepository.findByEmail(user.getEmail());
    if(isEmailExist!=null){
      throw new Exception("Email is already used ");
    }
  //    Create New User
    User createUser = new User();
    createUser.setEmail(user.getEmail());
    createUser.setAddresses(user.getAddresses());
    createUser.setFullName(user.getFullName());
    createUser.setRole(user.getRole());
    createUser.setPassword(passwordEncoder.encode(user.getPassword()));
  //    Save New User
    User saveUser= userRepository.save(createUser);
  //   Add Card to New User
    Card card = new Card();
    card.setCustomer(saveUser);
    cartRepository.save(card);
//   Add Information of New User to Authentication
    Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt=jwtProvider.generateToken(authentication);
    AuthResponse authResponse=new AuthResponse();
    authResponse.setJwt(jwt);
    authResponse.setMessage("Register user successfully");
    authResponse.setRole(user.getRole());
    return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
  }
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody String email,@RequestBody String password) throws Exception{
  User UserExist = userRepository.findByEmail(email);
  if(UserExist==null){
    throw new Exception("Email not found");
  }
    Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt=jwtProvider.generateToken(authentication);
    AuthResponse authResponse=new AuthResponse();
    authResponse.setRole(UserExist.getRole());
    authResponse.setMessage("Login is successfully");
    authResponse.setJwt(jwt);
    return new ResponseEntity<>(authResponse, HttpStatus.OK);

  }

}
