package com.hexlindia.drool.product.business.api.usecase;

import com.hexlindia.drool.product.dto.ProductAspectTemplatesDto;
import org.bson.types.ObjectId;

public interface Product {

    ProductAspectTemplatesDto getAspectTemplates(ObjectId id);
}
