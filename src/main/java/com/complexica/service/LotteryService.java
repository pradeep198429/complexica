package com.complexica.service;

import com.complexica.dto.LotteryNumbersDTO;
import com.complexica.dto.UserHistoryDTO;
import com.complexica.mapper.UserHistoryMapper;
import com.complexica.model.LotteryNumbers;
import com.complexica.model.User;
import com.complexica.repository.LotteryNumbersRepository;
import com.complexica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LotteryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LotteryNumbersRepository lotteryNumbersRepository;

    @Autowired
    private UserHistoryMapper userHistoryMapper;

    public LotteryNumbersDTO generateNumbers(String userName) {
        User user = userRepository.findByNameIgnoreCase(userName)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setName(userName.trim());
                    return userRepository.save(newUser);
                });

        List<Integer> numbers = new Random().ints(1, 46)
                .distinct()
                .limit(6)
                .sorted()
                .boxed()
                .collect(Collectors.toList());

        LotteryNumbers lotteryNumbers = new LotteryNumbers();
        lotteryNumbers.setNumbers(numbers);
        lotteryNumbers.setTimestamp(LocalDateTime.now());
        lotteryNumbers.setUser(user);
        lotteryNumbersRepository.save(lotteryNumbers);

        return new LotteryNumbersDTO(numbers);
    }

    public List<UserHistoryDTO> getUserHistory(String userName) {
        Optional<User> userOptional = userRepository.findByNameIgnoreCase(userName);
        if (userOptional.isPresent()) {
            List<LotteryNumbers> history = lotteryNumbersRepository.findByUserOrderByTimestampDesc(userOptional.get());
            return history.stream()
                    .map(userHistoryMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
