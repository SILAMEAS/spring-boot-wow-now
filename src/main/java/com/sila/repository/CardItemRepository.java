package com.sila.repository;

import com.sila.model.Card;
import com.sila.model.CardItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardItemRepository extends JpaRepository<CardItem,Long> {
}
