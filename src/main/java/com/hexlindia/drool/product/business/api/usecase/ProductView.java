package com.hexlindia.drool.product.business.api.usecase;

import com.hexlindia.drool.product.dto.ProductPageDto;

public interface ProductView {
    ProductPageDto getProductPageById(String id);
}
