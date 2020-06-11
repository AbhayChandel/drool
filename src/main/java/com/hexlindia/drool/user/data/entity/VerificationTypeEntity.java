package com.hexlindia.drool.user.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "VerificationTypeEntity")
@Data
@Table(name = "verification_type")
@AllArgsConstructor
@NoArgsConstructor
public class VerificationTypeEntity {

    @Id
    private int id;
    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationTypeEntity that = (VerificationTypeEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
