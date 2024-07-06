package com.complexica.service;

import com.complexica.dto.LotteryNumbersDTO;
import com.complexica.dto.UserHistoryDTO;
import com.complexica.mapper.UserHistoryMapper;
import com.complexica.model.LotteryNumbers;
import com.complexica.model.User;
import com.complexica.repository.LotteryNumbersRepository;
import com.complexica.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LotteryServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LotteryNumbersRepository lotteryNumbersRepository;

    @Mock
    private UserHistoryMapper userHistoryMapper;

    @InjectMocks
    private LotteryService lotteryService;

    private User user;
    private LotteryNumbers lotteryNumbers;
    private UserHistoryDTO userHistoryDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("testuser");

        lotteryNumbers = new LotteryNumbers();
        lotteryNumbers.setId(1L);
        lotteryNumbers.setNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
        lotteryNumbers.setTimestamp(LocalDateTime.now());
        lotteryNumbers.setUser(user);

        userHistoryDTO = new UserHistoryDTO();
        userHistoryDTO.setTimestamp(lotteryNumbers.getTimestamp());
        userHistoryDTO.setNumbers(lotteryNumbers.getNumbers());
    }

    @Test
    void testGenerateNumbers_NewUser() {
        when(userRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(lotteryNumbersRepository.save(any(LotteryNumbers.class))).thenReturn(lotteryNumbers);

        LotteryNumbersDTO result = lotteryService.generateNumbers("newuser");
        assertNotNull(result);
        assertEquals(6, result.getNumbers().size());
    }

    @Test
    void testGenerateNumbers_ExistingUser() {
        when(userRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(user));
        when(lotteryNumbersRepository.save(any(LotteryNumbers.class))).thenReturn(lotteryNumbers);

        LotteryNumbersDTO result = lotteryService.generateNumbers("testuser");
        assertNotNull(result);
        assertEquals(6, result.getNumbers().size());
    }

    @Test
    void testGetUserHistory_UserExists() {
        when(userRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(user));
        when(lotteryNumbersRepository.findByUserOrderByTimestampDesc(any(User.class)))
                .thenReturn(Collections.singletonList(lotteryNumbers));
        when(userHistoryMapper.toDto(any(LotteryNumbers.class))).thenReturn(userHistoryDTO);

        List<UserHistoryDTO> history = lotteryService.getUserHistory("testuser");
        assertNotNull(history);
        assertFalse(history.isEmpty());
        assertEquals(1, history.size());
    }

    @Test
    void testGetUserHistory_UserDoesNotExist() {
        when(userRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());

        List<UserHistoryDTO> history = lotteryService.getUserHistory("unknownuser");
        assertNotNull(history);
        assertTrue(history.isEmpty());
    }
}
