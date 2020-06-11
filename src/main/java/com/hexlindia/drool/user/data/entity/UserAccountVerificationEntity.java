package com.hexlindia.drool.user.data.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_account_verification")
public class UserAccountVerificationEntity {

    @EmbeddedId
    private UserAccountVerificationId id;

    boolean verified;
}
