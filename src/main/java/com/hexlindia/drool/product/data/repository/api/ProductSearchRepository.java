package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.data.doc.SearchProductRef;

import java.util.List;

public interface ProductSearchRepository {

    List<SearchProductRef> searchByTags(String searchString);
}
