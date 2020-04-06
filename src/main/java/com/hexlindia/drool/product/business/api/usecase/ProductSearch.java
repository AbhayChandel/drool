package com.hexlindia.drool.product.business.api.usecase;

import com.hexlindia.drool.product.dto.SearchProductDto;

import java.util.List;

public interface ProductSearch {


    List<SearchProductDto> searchByTags(String searchString);
}
