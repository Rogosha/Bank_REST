package com.example.bankcards.entity;

import com.example.bankcards.util.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Card> cards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<BlockRequest> blockRequests = new ArrayList<>();

    @Column
    private UserRole role;
}

