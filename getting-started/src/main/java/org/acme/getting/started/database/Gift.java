package org.acme.getting.started.database;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name = "Gift.findAll",
        query = "SELECT f FROM Gift f ORDER BY f.name",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
public class Gift {
    @Id
    @SequenceGenerator(name = "giftSeq", sequenceName = "gift_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "giftSeq")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gift_id")
    private List<GiftChildren> giftChildren;

    public void setGiftChildren(List<GiftChildren> giftChildren) {
        this.giftChildren = giftChildren;
    }

    public List<GiftChildren> getGiftChildren() {
        return giftChildren;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + " - childern: " +
                (giftChildren != null ? giftChildren.size() : "null");
    }
}
