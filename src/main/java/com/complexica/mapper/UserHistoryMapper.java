package com.complexica.mapper;

import com.complexica.dto.UserHistoryDTO;
import com.complexica.model.LotteryNumbers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserHistoryMapper {
    UserHistoryDTO toDto(LotteryNumbers lotteryNumbers);
}
