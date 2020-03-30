package com.hexlindia.drool.product.business.api.usecase;

import com.hexlindia.drool.product.dto.ReviewDto;

public interface ProductReview {

    ReviewDto save(ReviewDto reviewDto);
}
