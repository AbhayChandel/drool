package com.hexlindia.drool.user.data.repository.api;

import com.mongodb.client.result.UpdateResult;

public interface UserActivityRepository {

    UpdateResult addVideoLike(String videoId, String videoTitle, String userId);

}
