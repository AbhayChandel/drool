package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.data.doc.ProductDoc;

public interface ProductRepository {
    ProductDoc findById(String id);
}
