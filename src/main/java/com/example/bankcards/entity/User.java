package com.example.bankcards.entity;

import com.example.bankcards.util.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;


    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Card> cards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<BlockRequest> blockRequests = new ArrayList<>();

    @Column
    private UserRole role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.client;
    }
}

