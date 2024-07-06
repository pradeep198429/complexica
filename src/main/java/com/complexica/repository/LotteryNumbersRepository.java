package com.complexica.repository;

import com.complexica.model.LotteryNumbers;
import com.complexica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotteryNumbersRepository extends JpaRepository<LotteryNumbers, Long> {
    List<LotteryNumbers> findByUserOrderByTimestampDesc(User user);
}
