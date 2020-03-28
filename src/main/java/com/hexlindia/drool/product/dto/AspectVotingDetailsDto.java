package com.hexlindia.drool.product.dto;

import com.hexlindia.drool.common.dto.ProductRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AspectVotingDetailsDto {

    private List<AspectVotingDto> aspectVotingDtoList;
    private ProductRefDto productRefDto;
    private UserRefDto userRefDto;
}
