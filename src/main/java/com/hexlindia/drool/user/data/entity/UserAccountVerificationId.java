package com.hexlindia.drool.user.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountVerificationId implements Serializable {

    private long userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type", referencedColumnName = "id")
    private VerificationTypeEntity verificationType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccountVerificationId)) return false;
        UserAccountVerificationId that = (UserAccountVerificationId) o;
        return Objects.equals(getVerificationType(), that.getVerificationType()) &&
                Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVerificationType(), getUserId());
    }
}
