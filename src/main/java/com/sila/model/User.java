package com.sila.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sila.dto.RestaurantDto;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String fullName;
  private String email;
  private String password;
  private USER_ROLE role;
  @JsonIgnore
  @OneToMany
  private List<Order> orders=new ArrayList<>();
  @ElementCollection
  private List<RestaurantDto> favourites=new ArrayList<>();
  @OneToMany
  private List<Address> ds=new ArrayList<>();

}
