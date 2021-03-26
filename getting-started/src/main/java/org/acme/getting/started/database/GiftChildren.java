package org.acme.getting.started.database;

import javax.persistence.*;

@Entity
public class GiftChildren {

    private Long id;
    private String name;
    private Gift gift;

    @Id
    @SequenceGenerator(name = "giftSeq", sequenceName = "gift_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "giftSeq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name + "hi";
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(targetEntity = Gift.class)
    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }
}
