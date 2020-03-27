package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.ProductReview;
import com.hexlindia.drool.product.data.doc.TextReviewDoc;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import com.hexlindia.drool.product.dto.ReviewDto;
import com.hexlindia.drool.product.dto.mapper.TextReviewMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductReviewImpl implements ProductReview {

    private final ProductReviewRepository productReviewRepository;
    private final TextReviewMapper textReviewMapper;

    @Override
    public ReviewDto save(ReviewDto reviewDto) {
        TextReviewDoc textReviewDoc = textReviewMapper.toReviewDoc(reviewDto);
        textReviewDoc = productReviewRepository.save(textReviewDoc, new ObjectId(reviewDto.getProductRefDto().getId()));
        reviewDto.setId(textReviewDoc.getId().toHexString());
        return reviewDto;
    }
}
