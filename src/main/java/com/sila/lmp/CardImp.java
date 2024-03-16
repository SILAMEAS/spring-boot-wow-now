package com.sila.lmp;

import com.sila.model.Card;
import com.sila.repository.CardItemRepository;
import com.sila.repository.CardRepository;
import com.sila.request.CreateItemToCardRequset;
import com.sila.service.CardService;
import com.sila.service.FoodService;
import com.sila.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardImp implements CardService {
    private final CardRepository cardRepository;
    private final FoodService foodService;
    private final UserService userService;
    private final CardItemRepository cardItemRepository;

    @Override
    public Card addItemToCard(CreateItemToCardRequset req, String jwt) throws Exception {
        return null;
    }

    @Override
    public Card updateCardItemQty(Long cardId, int qty) throws Exception {
        return null;
    }

    @Override
    public Card removeItemFromCard(Long cardId, String jwt) throws Exception {
        return null;
    }

    @Override
    public Long calculateCardTotal(Card card) throws Exception {
        return null;
    }

    @Override
    public Card findCardById(Long cardId) throws Exception {
        return null;
    }

    @Override
    public Card findCardByUserId(Long userId) throws Exception {
        return null;
    }

    @Override
    public Card clearCard(Long userId) throws Exception {
        return null;
    }
}
