package com.hexlindia.drool.product.services.api.rest;

import com.hexlindia.drool.product.dto.ReviewDialogFormsDto;
import com.hexlindia.drool.product.dto.ReviewDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/${rest.uri.version}/product/review")
public interface ProductReviewRestService {

    @PostMapping("/save")
    ResponseEntity<ReviewDto> save(@RequestBody ReviewDto reviewDto);

    @GetMapping("/forms/{productId}/{brandId}")
    ResponseEntity<ReviewDialogFormsDto> getReviewDialogForms(@PathVariable("productId") ObjectId productId, @PathVariable("brandId") ObjectId brandId);
}
