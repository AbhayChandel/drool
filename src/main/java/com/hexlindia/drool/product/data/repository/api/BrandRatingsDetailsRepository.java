package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.data.doc.BrandRatingsDetailsDoc;
import org.bson.types.ObjectId;

public interface BrandRatingsDetailsRepository {

    ObjectId saveRatings(BrandRatingsDetailsDoc brandRatingsDetailsDoc);
}
