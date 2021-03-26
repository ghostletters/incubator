package org.acme.getting.started.pulsar;

import org.acme.getting.started.database.Gift;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Singleton
@Transactional
public class GiftFinder {

    @Inject
    EntityManager em;

    public Gift findGift(Long id) {
        Gift gift = em.find(Gift.class, id);
        if (gift != null) {
            gift.toString();
        }
        return gift;
    }
}
