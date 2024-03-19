package com.example.transactionlocktestpjt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.Builder;

@Entity
public class Pocket {

    @Id
    @GeneratedValue
    private Long pocketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long point;

    @Version
    private Integer version;

    public Long addPoint(Long inputPoint) {
        this.point = this.point + inputPoint;
        return this.point;
    }

    protected Pocket() {}

    @Builder
    public Pocket(Long pocketId, User user, Long point) {
        this.pocketId = pocketId;
        this.user = user;
        this.point = point;
    }

    public Long getPocketId() {
        return pocketId;
    }

    public User getUser() {
        return user;
    }

    public Long getPoint() {
        return point;
    }
}

