package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.product.dto.AspectVotingDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProductReviewRepository {

    ReviewDoc save(ReviewDoc reviewDoc, ObjectId productId, List<AspectVotingDto> aspectVotingDtoList);
}
