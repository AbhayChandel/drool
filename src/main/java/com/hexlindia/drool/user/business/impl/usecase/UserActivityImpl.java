package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.user.data.doc.ActionType;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepositoryMongo;
import com.hexlindia.drool.violation.data.doc.ViolationReportRef;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserActivityImpl implements UserActivity {

    private final UserActivityRepositoryMongo userActivityRepositoryMongo;


    public UserActivityImpl(UserActivityRepositoryMongo userActivityRepositoryMongo) {
        this.userActivityRepositoryMongo = userActivityRepositoryMongo;
    }

    @Override
    public UpdateResult add(ObjectId userId, ActionType actionType, PostRef postRef) {
        postRef.setDateTime(LocalDateTime.now());
        return this.userActivityRepositoryMongo.add(userId, actionType, postRef);
    }

    @Override
    public UpdateResult delete(ObjectId userId, ActionType actionType, PostRef postRef) {
        return this.userActivityRepositoryMongo.delete(userId, actionType, postRef);
    }

    @Override
    public UpdateResult update(ObjectId userId, ActionType actionType, PostRef postRef) {
        return this.userActivityRepositoryMongo.update(userId, actionType, postRef);
    }

    @Override
    public boolean addViolation(ObjectId postOwnerId, ObjectId reportingUserId, ViolationReportRef violationReportRef) {
        UpdateResult resultAddViolation = this.userActivityRepositoryMongo.addViolation(postOwnerId, violationReportRef);
        if (resultAddViolation.getModifiedCount() > 0 || resultAddViolation.getUpsertedId() != null) {
            return this.userActivityRepositoryMongo.addReportedViolation(reportingUserId, violationReportRef).getModifiedCount() > 0;
        }
        return false;
    }
}
