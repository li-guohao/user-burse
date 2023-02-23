package com.example.userburse.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ub_burse")
public class BurseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public BurseEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public BurseEntity setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BurseEntity setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}
