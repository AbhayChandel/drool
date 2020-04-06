package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.ProductSearch;
import com.hexlindia.drool.product.data.repository.api.ProductSearchRepository;
import com.hexlindia.drool.product.dto.SearchProductDto;
import com.hexlindia.drool.product.dto.mapper.SearchProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductSearchImpl implements ProductSearch {

    private final ProductSearchRepository productSearchRepository;
    private final SearchProductMapper searchProductMapper;

    @Override
    public List<SearchProductDto> searchByTags(String searchString) {
        return searchProductMapper.toDtoList(productSearchRepository.searchByTags(searchString));
    }
}
