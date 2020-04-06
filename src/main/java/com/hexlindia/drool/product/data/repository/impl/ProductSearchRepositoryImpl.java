package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.SearchProductRef;
import com.hexlindia.drool.product.data.repository.api.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements ProductSearchRepository {

    private final MongoOperations mongoOperations;

    private static final String PRODUCTS_COLLECTION_NAME = "products";

    @Override
    public List<SearchProductRef> searchByTags(String searchString) {

        Criteria criteria = new Criteria().andOperator(getRegexCriterias(searchString));
        MatchOperation match = match(criteria);
        ProjectionOperation project = project("brand").andExpression("concat($brand.name, ' ',name)").as("name");

        AggregationResults<SearchProductRef> results = this.mongoOperations.aggregate(Aggregation.newAggregation(
                match,
                project,
                Aggregation.limit(5)
        ), PRODUCTS_COLLECTION_NAME, SearchProductRef.class);

        return results.getMappedResults();
    }

    private Criteria[] getRegexCriterias(String searchString) {
        String[] tags = getTags(searchString);
        Criteria[] regexCriterias = new Criteria[tags.length];
        for (int i = 0; i < regexCriterias.length; i++) {
            regexCriterias[i] = Criteria.where("search_tags").regex("^" + tags[i]);
        }
        return regexCriterias;
    }

    private String[] getTags(String searchString) {
        if (searchString == null || searchString.trim().isEmpty()) {
            return new String[0];
        }
        return searchString.trim().toLowerCase().split(" ");
    }


}
