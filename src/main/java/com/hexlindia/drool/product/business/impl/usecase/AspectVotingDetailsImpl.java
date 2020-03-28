package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.AspectVotingDetails;
import com.hexlindia.drool.product.data.repository.api.AspectVotingDetailsRepository;
import com.hexlindia.drool.product.dto.AspectVotingDetailsDto;
import com.hexlindia.drool.product.dto.mapper.AspectVotingDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AspectVotingDetailsImpl implements AspectVotingDetails {

    private final AspectVotingDetailsMapper aspectVotingDetailsMapper;
    private final AspectVotingDetailsRepository aspectVotingDetailsRepository;

    @Override
    public ObjectId save(AspectVotingDetailsDto aspectVotingDetailsDto) {
        return aspectVotingDetailsRepository.save(aspectVotingDetailsMapper.toDoc(aspectVotingDetailsDto));
    }
}
