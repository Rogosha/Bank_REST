package com.example.bankcards.entity;

import com.example.bankcards.util.BlockStatus;
import jakarta.persistence.*;

@Entity
public class BlockRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "admin_id ")
    private User admin;

    @Column
    private BlockStatus blockStatus;

}
