package com.hexlindia.drool.common.dto;

import com.hexlindia.drool.common.dto.validation.UserRefDeleteValidation;
import com.hexlindia.drool.common.dto.validation.UserRefValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserRefDto {

    @NotEmpty(message = "User Id is missing", groups = {UserRefValidation.class, UserRefDeleteValidation.class})
    private String id;

    @NotEmpty(message = "Username is missing", groups = {UserRefValidation.class})
    private String username;

    public UserRefDto(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserRefDto() {
    }
}
