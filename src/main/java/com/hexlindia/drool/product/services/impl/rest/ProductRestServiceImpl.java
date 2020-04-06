package com.hexlindia.drool.product.services.impl.rest;

import com.hexlindia.drool.product.business.api.usecase.ProductSearch;
import com.hexlindia.drool.product.services.api.rest.ProductRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductRestServiceImpl implements ProductRestService {

    private final ProductSearch productSearch;
}
