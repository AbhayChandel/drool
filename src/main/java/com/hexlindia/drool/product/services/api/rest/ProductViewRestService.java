package com.hexlindia.drool.product.services.api.rest;

import com.hexlindia.drool.product.dto.ProductPageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/view/product")
public interface ProductViewRestService {

    @GetMapping("/page/id/{id}")
    ResponseEntity<ProductPageDto> getProductPageById(@PathVariable("id") String id);
}
