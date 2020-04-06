package com.hexlindia.drool.product.services.impl.rest;

import com.hexlindia.drool.product.business.api.usecase.ProductSearch;
import com.hexlindia.drool.product.dto.SearchProductDto;
import com.hexlindia.drool.product.services.api.rest.ProductSearchRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductSearchRestServiceImpl implements ProductSearchRestService {

    private final ProductSearch productSearch;

    @Override
    public ResponseEntity<List<SearchProductDto>> searchByTags(String searchString) {
        return ResponseEntity.ok(productSearch.searchByTags(searchString));
    }
}
