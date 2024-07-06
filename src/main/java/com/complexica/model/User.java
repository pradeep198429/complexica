package com.complexica.model;

import lombok.Data;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LotteryNumbers> lotteryNumbersList = new ArrayList<>();
}
