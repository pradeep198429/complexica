package com.complexica.model;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class LotteryNumbers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "lottery_numbers_values", joinColumns = @JoinColumn(name = "lottery_numbers_id"))
    @Column(name = "number")
    private List<Integer> numbers;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
