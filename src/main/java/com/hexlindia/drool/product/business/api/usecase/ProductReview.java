package com.hexlindia.drool.product.business.api.usecase;

import com.hexlindia.drool.product.dto.ReviewDialogFormsDto;
import com.hexlindia.drool.product.dto.ReviewDto;
import org.bson.types.ObjectId;

public interface ProductReview {

    ReviewDto save(ReviewDto reviewDto);

    ReviewDialogFormsDto getReviewDialogForms(ObjectId productId, ObjectId brandId);
}
