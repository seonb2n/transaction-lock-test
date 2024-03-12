package com.example.transactionlocktestpjt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @OneToMany
    private List<Pocket> pocketList = new ArrayList<Pocket>();

}
