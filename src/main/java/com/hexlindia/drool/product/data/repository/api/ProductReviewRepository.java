package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.data.doc.ReviewDoc;
import org.bson.types.ObjectId;

public interface ProductReviewRepository {

    ReviewDoc save(ReviewDoc reviewDoc, ObjectId productId);
}
