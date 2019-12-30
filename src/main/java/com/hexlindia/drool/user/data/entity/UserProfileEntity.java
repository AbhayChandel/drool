package com.hexlindia.drool.user.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_id_generator")
    @SequenceGenerator(name = "user_profile_id_generator", sequenceName = "user_profile_id_seq", allocationSize = 1)
    private Long id;

    private String username;
    private long mobile;
    private String city;
    private char gender;

    public UserProfileEntity(String username, long mobile, String city, char gender) {
        this.username = username;
        this.mobile = mobile;
        this.city = city;
        this.gender = gender;
    }

    public UserProfileEntity() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public long getMobile() {
        return mobile;
    }

    public String getCity() {
        return city;
    }

    public char getGender() {
        return gender;
    }
}
