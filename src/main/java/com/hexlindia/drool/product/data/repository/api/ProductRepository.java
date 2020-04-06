package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.data.doc.ProductDoc;
import org.bson.types.ObjectId;

public interface ProductRepository {
    ProductDoc findById(ObjectId id);
}
