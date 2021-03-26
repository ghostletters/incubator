package org.acme.getting.started.database;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SantaClausService {
    @Inject
    EntityManager em;

    @Transactional
    public Gift createGift(String giftDescription) {
        Gift gift = new Gift();
        gift.setName(giftDescription);

        GiftChildren giftChildrenOne = new GiftChildren();
        giftChildrenOne.setName("children 1");
        GiftChildren giftChildrenTwo = new GiftChildren();
        giftChildrenTwo.setName("children 2");

        gift.setGiftChildren(List.of(giftChildrenOne, giftChildrenTwo));
        em.persist(gift);

        return gift;
    }
}

