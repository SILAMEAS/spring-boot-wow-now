package com.sila.service;

import com.sila.model.Card;
import com.sila.model.CardItem;
import com.sila.dto.request.CreateItemToCardRequset;

public interface CardService {
    public CardItem addItemToCard(CreateItemToCardRequset req, String jwt)throws Exception;
    public CardItem updateCardItemQty(Long cardId,int qty)throws  Exception;
    public Card removeItemFromCard(Long cardId,String jwt)throws  Exception;
    public Long calculateCardTotal(Card card)throws Exception;
    public Card findCardById(Long cardId)throws Exception;
    public Card findCardByUserId(Long userId)throws Exception;
    public Card clearCard(String jwt)throws Exception;

}
