package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserActivityRepositoryImplTest {

    private final UserActivityRepository userActivityRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserActivityRepositoryImplTest(UserActivityRepository userActivityRepository, MongoTemplate mongoTemplate) {
        this.userActivityRepository = userActivityRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Test
    void addVideoLike() {
        UpdateResult updateResult = this.userActivityRepository.addVideoLike("abc", "This is a testing video", "123");
        assertNotNull(updateResult.getModifiedCount());
    }
}