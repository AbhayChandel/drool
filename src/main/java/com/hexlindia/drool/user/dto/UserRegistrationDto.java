package com.hexlindia.drool.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Getter
@Setter
public class UserRegistrationDto {

    @JsonProperty("account")
    @Valid
    private UserAccountDto userAccountDto;

    @JsonProperty("profile")
    private UserProfileDto userProfileDto;
}
