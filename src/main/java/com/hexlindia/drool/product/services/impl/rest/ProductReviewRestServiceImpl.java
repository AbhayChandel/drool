package com.hexlindia.drool.product.services.impl.rest;

import com.hexlindia.drool.product.business.api.usecase.ProductReview;
import com.hexlindia.drool.product.dto.ReviewDialogFormsDto;
import com.hexlindia.drool.product.dto.ReviewDto;
import com.hexlindia.drool.product.services.api.rest.ProductReviewRestService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductReviewRestServiceImpl implements ProductReviewRestService {

    private final ProductReview productReview;

    @Override
    public ResponseEntity<ReviewDto> save(ReviewDto reviewDto) {
        return ResponseEntity.ok(productReview.save(reviewDto));
    }

    @Override
    public ResponseEntity<ReviewDialogFormsDto> getReviewDialogForms(ObjectId productId, ObjectId brandId) {
        ReviewDialogFormsDto reviewDialogFormsDto = productReview.getReviewDialogForms(productId, brandId);
        return ResponseEntity.ok(reviewDialogFormsDto);
    }
}
