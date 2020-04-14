package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.data.repository.api.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    private final MongoOperations mongoOperations;

    @Autowired
    public ProductRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public ProductDoc findById(ObjectId id) {
        return mongoOperations.findOne(query(where("id").is(id).andOperator(where("active").is(true))), ProductDoc.class);
    }

}
