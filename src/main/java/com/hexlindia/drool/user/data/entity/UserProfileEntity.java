package com.hexlindia.drool.user.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_profile")
public class UserProfileEntity {

    @Id
    private Long id;
    private String name;
    private String city;
    private String gender;
    private LocalDateTime joinDate;
}
