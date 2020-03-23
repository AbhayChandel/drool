package com.hexlindia.drool.product.services.impl.rest;

import com.hexlindia.drool.product.business.api.usecase.ProductView;
import com.hexlindia.drool.product.dto.ProductPageDto;
import com.hexlindia.drool.product.services.api.rest.ProductViewRestService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductViewRestServiceImpl implements ProductViewRestService {

    private final ProductView productView;

    @Override
    public ResponseEntity<ProductPageDto> getProductPageById(ObjectId id) {
        return ResponseEntity.ok(productView.getProductPageById(id));
    }
}
