package com.hexlindia.drool.product.data.repository.api;

import org.bson.types.ObjectId;

import java.util.List;

public interface BrandRepository {

    List<String> getRatingMetrics(ObjectId id);
}
