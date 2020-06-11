package com.hexlindia.drool.user.data.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_account")
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_account_id_seq", allocationSize = 1)
    private Long id;
    private String email;
    private String password;
    private String username;
    private String mobile;
    private boolean active;
}
