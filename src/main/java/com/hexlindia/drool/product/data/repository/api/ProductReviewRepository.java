package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.data.doc.ProductAspectTemplates;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.product.dto.AspectVotingDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProductReviewRepository {

    boolean setVideoId(ObjectId productId, ObjectId reviewId, ObjectId videoId);

    ReviewDoc save(ReviewDoc reviewDoc, ObjectId productId, List<AspectVotingDto> aspectVotingDtoList);

    ProductAspectTemplates getAspectTemplates(ObjectId id);
}
