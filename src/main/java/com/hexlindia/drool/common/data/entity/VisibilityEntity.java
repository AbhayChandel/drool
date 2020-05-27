package com.hexlindia.drool.common.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "visibility")
@Data
public class VisibilityEntity {

    @Id
    private int id;
    private String visibility;
}
