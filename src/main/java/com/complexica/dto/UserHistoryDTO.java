package com.complexica.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserHistoryDTO {
    private LocalDateTime timestamp;
    private List<Integer> numbers;
}
