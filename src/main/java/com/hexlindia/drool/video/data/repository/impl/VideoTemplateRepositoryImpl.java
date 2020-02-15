package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.data.repository.api.VideoTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class VideoTemplateRepositoryImpl implements VideoTemplateRepository {

    private final MongoOperations mongoOperations;

    @Autowired
    public VideoTemplateRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public VideoDoc insert(VideoDoc videoDoc) {
        return this.mongoOperations.insert(videoDoc);
    }

    @Override
    public VideoDoc findByIdAndActiveTrue(String id) {
        return mongoOperations.findOne(query(where("id").is(id).andOperator(where("active").is(true))), VideoDoc.class);
    }

    @Override
    public void incrementLikes(String id) {
        mongoOperations.updateFirst(new Query(where("id").is(id)),
                new Update().inc("likes", 1), VideoDoc.class);
    }

    @Override
    public void decrementLikes(String id) {
        mongoOperations.updateFirst(new Query(where("id").is(id)),
                new Update().inc("likes", -1), VideoDoc.class);
    }
}
