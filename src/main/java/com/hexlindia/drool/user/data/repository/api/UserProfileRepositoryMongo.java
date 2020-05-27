package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.user.data.doc.UserProfileDoc;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface UserProfileRepositoryMongo {
    UserProfileDoc save(UserProfileDoc userProfileDoc);

    Optional<UserProfileDoc> findById(ObjectId id);

    Optional<UserProfileDoc> findByUsername(String username);
}
