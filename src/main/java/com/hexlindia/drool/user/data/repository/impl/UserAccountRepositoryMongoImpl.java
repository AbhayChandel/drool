package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepositoryMongo;
import com.hexlindia.drool.user.exception.EmailExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class UserAccountRepositoryMongoImpl implements UserAccountRepositoryMongo {

    private final MongoOperations mongoOperations;

    @Override
    public UserAccountDoc save(UserAccountDoc userAccountDoc) {
        if (findByEmail(userAccountDoc.getEmailId()).isPresent()) {
            throw new EmailExistException("Email " + userAccountDoc.getEmailId() + " already exists");
        }
        return mongoOperations.save(userAccountDoc);
    }

    @Override
    public Optional<UserAccountDoc> findByEmail(String email) {
        UserAccountDoc userAccountDoc = mongoOperations.findOne(query(where("emailId").is(email).andOperator(where("active").is(true))), UserAccountDoc.class);
        return userAccountDoc == null ? Optional.empty() : Optional.of(userAccountDoc);
    }


}
