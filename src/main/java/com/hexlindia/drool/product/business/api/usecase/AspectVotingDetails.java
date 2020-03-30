package com.hexlindia.drool.product.business.api.usecase;

import com.hexlindia.drool.product.dto.AspectVotingDetailsDto;
import org.bson.types.ObjectId;

public interface AspectVotingDetails {

    ObjectId save(AspectVotingDetailsDto aspectVotingDetailsDto);
}
