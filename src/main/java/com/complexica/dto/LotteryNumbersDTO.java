package com.complexica.dto;

import lombok.Data;

import java.util.List;

@Data
public class LotteryNumbersDTO {
    private List<Integer> numbers;

    public LotteryNumbersDTO(List<Integer> numbers) {
        this.numbers = numbers;
    }
}
