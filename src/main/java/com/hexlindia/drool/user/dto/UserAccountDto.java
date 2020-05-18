package com.hexlindia.drool.user.dto;

import com.hexlindia.drool.user.dto.validation.NewAccount;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserAccountDto {

    String id;

    @NotEmpty(message = "Email Id cannot be empty", groups = {NewAccount.class})
    @Email(message = "Email Id is not correct", groups = {NewAccount.class})
    String emailId;

    @NotEmpty(message = "Password cannot be empty", groups = {NewAccount.class})
    String password;

    String username;

    String mobile;
}
