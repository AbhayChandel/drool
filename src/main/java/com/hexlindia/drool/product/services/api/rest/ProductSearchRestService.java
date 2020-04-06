package com.hexlindia.drool.product.services.api.rest;

import com.hexlindia.drool.product.dto.SearchProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/${rest.uri.version}/view/product/search")
public interface ProductSearchRestService {

    @GetMapping("/tags/{searchString}")
    ResponseEntity<List<SearchProductDto>> searchByTags(@PathVariable("searchString") String searchString);
}
