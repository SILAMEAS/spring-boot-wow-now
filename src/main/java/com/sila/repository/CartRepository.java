package com.sila.repository;

import com.sila.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Card,Long> {

}
