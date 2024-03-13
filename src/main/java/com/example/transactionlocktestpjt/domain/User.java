package com.example.transactionlocktestpjt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @OneToMany(mappedBy = "user")
    private List<Pocket> pocketList = new ArrayList<Pocket>();


    public Long getUserId() {
        return userId;
    }

    public List<Pocket> getPocketList() {
        return pocketList;
    }
}
