package com.sila.controller.auth;
import com.sila.config.JwtProvider;
import com.sila.model.Card;
import com.sila.utlis.enums.USER_ROLE;
import com.sila.model.User;
import com.sila.repository.CardRepository;
import com.sila.repository.UserRepository;
import com.sila.dto.request.LoginRequest;
import com.sila.dto.response.AuthResponse;
import com.sila.config.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final CustomerUserDetailsService customerUserDetailsService;
  private final CardRepository cartRepository;
  private Authentication authenticate(String email, String password) {
    UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
    if(userDetails == null){
      throw new BadCredentialsException("Invalid username or email ...");
    }
    if(!passwordEncoder.matches(password,userDetails.getPassword())){
      throw new BadCredentialsException("Invalid password ...");
    }
    return  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
  }
  @PostMapping("/sign-up")
  public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
    User isEmailExist = userRepository.findByEmail(user.getEmail());
    if(isEmailExist != null){
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
    User saveUser = userRepository.save(createUser);
  //   Add Card to New User
    Card card = new Card();
    card.setCustomer(saveUser);
    cartRepository.save(card);
//   Add Information of New User to Authentication
    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt=jwtProvider.generateToken(authentication);
    AuthResponse authResponse = new AuthResponse();
    authResponse.setJwt(jwt);
    authResponse.setMessage("Register user successfully");
    authResponse.setRole(user.getRole());
    return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
  }
  @PostMapping("/sign-in")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) throws Exception{
    String email = req.getEmail();
    String password = req.getPassword();
try {
  //  auth passing email and password to get authorization
  Authentication authentication = authenticate(email,password);
  Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
  String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
  String jwt = jwtProvider.generateToken(authentication);
//  Custom response data
  AuthResponse authResponse = new AuthResponse();
  authResponse.setJwt(jwt);
  authResponse.setMessage("login successfully");
  authResponse.setRole(USER_ROLE.valueOf(role));
  return new ResponseEntity<>(authResponse, HttpStatus.OK);
  }catch (Exception e){
    throw new BadRequestException(email+" is not found. Please sign up new account with our application");
  }


  }




}
