package com.hexlindia.drool.product.services.api.rest;

import com.hexlindia.drool.product.dto.ReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/product/review")
public interface ProductReviewRestService {

    @PostMapping("/save")
    ResponseEntity<ReviewDto> save(@RequestBody ReviewDto reviewDto);
}
