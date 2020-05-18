package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.user.data.doc.ActionType;
import com.hexlindia.drool.violation.data.doc.ViolationReportRef;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

public interface UserActivityRepositoryMongo {

    UpdateResult add(ObjectId userId, ActionType actionType, PostRef postRef);

    UpdateResult delete(ObjectId userId, ActionType actionType, PostRef postRef);

    UpdateResult update(ObjectId userId, ActionType actionType, PostRef postRef);

    UpdateResult addViolation(ObjectId userId, ViolationReportRef violationReportRef);

    UpdateResult addReportedViolation(ObjectId userId, ViolationReportRef violationReportRef);
}
