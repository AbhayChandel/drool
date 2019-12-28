package com.hexlindia.drool.user.business.api.to.mapper;

import com.hexlindia.drool.user.business.api.to.UserAccountTo;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAccountMapperTest {

    private final UserAccountMapper mapper = Mappers.getMapper(UserAccountMapper.class);

    @Test
    void testMappingEntityToTransferObject() {
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setId(3L);
        userAccountEntity.setEmail("sonam99@gmail.com");
        userAccountEntity.setPassword("$2y$12$nkEeE1P.hWfg1iqhp8JWOea9F7lEEzBi07ZdGs1ujrVJM5YVYnQqi");
        UserAccountTo userAccountTo = mapper.toTransferObject(userAccountEntity);
        assertEquals("sonam99@gmail.com", userAccountTo.getEmail());
        assertEquals(3L, userAccountTo.getId());
    }
}
