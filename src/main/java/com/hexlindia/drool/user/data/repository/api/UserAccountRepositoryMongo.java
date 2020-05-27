package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.user.data.doc.UserAccountDoc;

import java.util.Optional;

public interface UserAccountRepositoryMongo {

    UserAccountDoc save(UserAccountDoc userAccountDoc);

    Optional<UserAccountDoc> findByEmail(String email);
}
