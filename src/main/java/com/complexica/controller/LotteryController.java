package com.complexica.controller;

import com.complexica.dto.GenerateRequest;
import com.complexica.dto.LotteryNumbersDTO;
import com.complexica.dto.UserHistoryDTO;
import com.complexica.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @PostMapping("/generate")
    public LotteryNumbersDTO generateNumbers(@RequestBody GenerateRequest request) {
        return lotteryService.generateNumbers(request.getName());
    }

    @GetMapping("/history/{userName}")
    public List<UserHistoryDTO> getUserHistory(@PathVariable String userName) {
        return lotteryService.getUserHistory(userName);
    }
}
