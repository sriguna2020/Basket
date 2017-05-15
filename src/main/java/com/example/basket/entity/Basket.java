package com.example.basket.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<BasketPosition> positions = new ArrayList<>();

    private Long userId;

    public Basket() {
        // hibernate
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BasketPosition> getPositions() {
        return positions;
    }

    public void setBasketPositions(List<BasketPosition> products) {
        this.positions = positions;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
