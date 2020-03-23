package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.Product;
import com.hexlindia.drool.product.data.repository.api.ProductRepository;
import com.hexlindia.drool.product.dto.ProductAspectTemplatesDto;
import com.hexlindia.drool.product.dto.mapper.ProductAspectTemplatesMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductImpl implements Product {

    private final ProductRepository productRepository;
    private final ProductAspectTemplatesMapper productAspectTemplatesMapper;

    @Override
    public ProductAspectTemplatesDto getAspectTemplates(ObjectId id) {
        return productAspectTemplatesMapper.toDto(this.productRepository.getAspectTemplates(id));
    }
}
