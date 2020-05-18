package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    //Optional<UserAccountEntity> findByEmailOrUsernameOrMobile(String email, String username, String mobile);

    @Query("select u from UserAccountEntity u where u.email = :userIdentifier or u.username = :userIdentifier")
    Optional<UserAccountEntity> findUser(@Param("userIdentifier") String userIdentifier);

}
