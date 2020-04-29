package com.hexlindia.drool.user.business.api.usecase;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.user.data.doc.ActionType;
import com.hexlindia.drool.violation.data.doc.ViolationReportRef;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

public interface UserActivity {

    UpdateResult add(ObjectId userId, ActionType actionType, PostRef postRef);

    UpdateResult delete(ObjectId userId, ActionType actionType, PostRef postRef);

    UpdateResult update(ObjectId userId, ActionType actionType, PostRef postRef);

    boolean addViolation(ObjectId postOwnerId, ObjectId reportingUserId, ViolationReportRef violationReportRef);


}
