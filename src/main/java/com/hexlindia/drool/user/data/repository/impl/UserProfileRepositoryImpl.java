package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.user.data.doc.UserProfileDoc;
import com.hexlindia.drool.user.data.repository.api.UserProfileRepository;
import com.hexlindia.drool.user.exception.UsernameExistException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileRepository {

    private final MongoOperations mongoOperations;

    @Override
    public UserProfileDoc save(UserProfileDoc userProfileDoc) {
        if (findByUsername(userProfileDoc.getUsername()).isPresent()) {
            throw new UsernameExistException("Username " + userProfileDoc.getUsername() + " already exists");
        }
        return mongoOperations.save(userProfileDoc);
    }

    public Optional<UserProfileDoc> findByUsername(String username) {
        UserProfileDoc userProfileDoc = mongoOperations.findOne(query(where("username").is(username)), UserProfileDoc.class);
        return userProfileDoc == null ? Optional.empty() : Optional.of(userProfileDoc);
    }

    @Override
    public Optional<UserProfileDoc> findById(ObjectId id) {
        UserProfileDoc userProfileDoc = mongoOperations.findOne(query(where("id").is(id)), UserProfileDoc.class);
        return userProfileDoc == null ? Optional.empty() : Optional.of(userProfileDoc);
    }
}
