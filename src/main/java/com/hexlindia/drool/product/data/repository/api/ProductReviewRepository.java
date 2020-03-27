package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.data.doc.TextReviewDoc;
import org.bson.types.ObjectId;

public interface ProductReviewRepository {

    TextReviewDoc save(TextReviewDoc textReviewDoc, ObjectId productId);
}
