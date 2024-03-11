package com.sila.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public enum USER_ROLE {
  ROLE_CUSTOMER,
  ROLE_RESTAURANT_OWNER,
  ROLE_ADMIN;

}
