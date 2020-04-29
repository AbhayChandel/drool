package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.user.data.doc.ActionType;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import com.hexlindia.drool.violation.data.doc.ViolationReportRef;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserActivityImpl implements UserActivity {

    private final UserActivityRepository userActivityRepository;


    public UserActivityImpl(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    @Override
    public UpdateResult add(ObjectId userId, ActionType actionType, PostRef postRef) {
        postRef.setDateTime(LocalDateTime.now());
        return this.userActivityRepository.add(userId, actionType, postRef);
    }

    @Override
    public UpdateResult delete(ObjectId userId, ActionType actionType, PostRef postRef) {
        return this.userActivityRepository.delete(userId, actionType, postRef);
    }

    @Override
    public UpdateResult update(ObjectId userId, ActionType actionType, PostRef postRef) {
        return this.userActivityRepository.update(userId, actionType, postRef);
    }

    @Override
    public boolean addViolation(ObjectId postOwnerId, ObjectId reportingUserId, ViolationReportRef violationReportRef) {
        UpdateResult resultAddViolation = this.userActivityRepository.addViolation(postOwnerId, violationReportRef);
        if (resultAddViolation.getModifiedCount() > 0 || resultAddViolation.getUpsertedId() != null) {
            return this.userActivityRepository.addReportedViolation(reportingUserId, violationReportRef).getModifiedCount() > 0;
        }
        return false;
    }
}
