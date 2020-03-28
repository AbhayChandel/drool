package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.AspectVotingDetailsDoc;
import com.hexlindia.drool.product.data.repository.api.AspectVotingDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AspectVotingDetailsRepositoryImpl implements AspectVotingDetailsRepository {

    private final MongoOperations mongoOperations;

    @Override
    public ObjectId save(AspectVotingDetailsDoc aspectVotingDetailsDoc) {
        aspectVotingDetailsDoc = this.mongoOperations.save(aspectVotingDetailsDoc);
        return aspectVotingDetailsDoc != null ? aspectVotingDetailsDoc.getId() : null;
    }
}
