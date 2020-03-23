package com.hexlindia.drool.product.business.api.usecase;

import com.hexlindia.drool.product.dto.ProductPageDto;
import org.bson.types.ObjectId;

public interface ProductView {
    ProductPageDto getProductPageById(ObjectId id);
}
