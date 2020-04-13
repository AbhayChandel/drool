package com.hexlindia.drool.user.dto;

import com.hexlindia.drool.user.dto.validation.NewAccount;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserAccountDto {

    @NotEmpty(message = "Email Id cannot be empty", groups = {NewAccount.class})
    @Email(message = "Email Id is not correct", groups = {NewAccount.class})
    String emailId;

    @NotEmpty(message = "Password cannot be empty", groups = {NewAccount.class})
    String password;

    String username;
}
