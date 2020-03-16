package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.ProductView;
import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.data.repository.api.ProductRepository;
import com.hexlindia.drool.product.dto.ProductPageDto;
import com.hexlindia.drool.product.dto.mapper.ProductDocDtoMapper;
import com.hexlindia.drool.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductViewImpl implements ProductView {

    private final ProductRepository productRepository;
    private final ProductDocDtoMapper productDocDtoMapper;

    @Override
    public ProductPageDto getProductPageById(String id) {
        ProductDoc productDoc = productRepository.findById(id);
        if (productDoc != null) {
            return new ProductPageDto(productDocDtoMapper.toDto(productDoc));
        }
        throw new ProductNotFoundException("Product with id " + id + " not found");
    }
}
