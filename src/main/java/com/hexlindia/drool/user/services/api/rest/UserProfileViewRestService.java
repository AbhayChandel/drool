package com.hexlindia.drool.user.services.api.rest;

import com.hexlindia.drool.user.dto.ContributionSummaryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/view/profile")
public interface UserProfileViewRestService {

    @GetMapping(value = "/contributions/id/{id}")
    ResponseEntity<ContributionSummaryDto> getContributionSummary(@PathVariable("id") String userId);
}
