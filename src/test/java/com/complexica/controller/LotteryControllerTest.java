package com.complexica.controller;

import com.complexica.dto.GenerateRequest;
import com.complexica.dto.LotteryNumbersDTO;
import com.complexica.dto.UserHistoryDTO;
import com.complexica.service.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LotteryControllerTest {

    @Mock
    private LotteryService lotteryService;

    @InjectMocks
    private LotteryController lotteryController;

    private MockMvc mockMvc;
    private LotteryNumbersDTO lotteryNumbersDTO;
    private UserHistoryDTO userHistoryDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController).build();

        lotteryNumbersDTO = new LotteryNumbersDTO(Arrays.asList(1, 2, 3, 4, 5, 6));
        userHistoryDTO = new UserHistoryDTO();
        userHistoryDTO.setTimestamp(LocalDateTime.now());
        userHistoryDTO.setNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
    }

    @Test
    void testGenerateNumbers() throws Exception {
        GenerateRequest request = new GenerateRequest("testuser");
        when(lotteryService.generateNumbers("testuser")).thenReturn(lotteryNumbersDTO);

        mockMvc.perform(post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"testuser\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numbers").isArray())
                .andExpect(jsonPath("$.numbers.length()").value(6));
    }

    @Test
    void testGetUserHistory() throws Exception {
        when(lotteryService.getUserHistory("testuser")).thenReturn(Collections.singletonList(userHistoryDTO));

        mockMvc.perform(get("/api/history/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numbers").isArray())
                .andExpect(jsonPath("$[0].numbers.length()").value(6));
    }
}
