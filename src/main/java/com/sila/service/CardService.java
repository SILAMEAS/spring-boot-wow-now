package com.sila.service;

import com.sila.model.Card;
import com.sila.request.CreateItemToCardRequset;

public interface CardService {
    public Card addItemToCard(CreateItemToCardRequset req, String jwt)throws Exception;
    public Card updateCardItemQty(Long cardId,int qty)throws  Exception;
    public Card removeItemFromCard(Long cardId,String jwt)throws  Exception;
    public Long calculateCardTotal(Card card)throws Exception;
    public Card findCardById(Long cardId)throws Exception;
    public Card findCardByUserId(Long userId)throws Exception;
    public Card clearCard(Long userId)throws Exception;

}
