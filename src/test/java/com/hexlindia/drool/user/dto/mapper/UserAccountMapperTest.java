package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.dto.UserAccountDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserAccountMapperTest {

    @Autowired
    UserAccountMapper userAccountMapper;

    @Test
    void toDto() {
        ObjectId id = new ObjectId();
        UserAccountDoc userAccountDoc = new UserAccountDoc();
        userAccountDoc.setId(id);
        userAccountDoc.setEmailId("nishant@gmail.com");
        userAccountDoc.setPassword("nishant");
        userAccountDoc.setActive(true);

        UserAccountDto userAccountDto = userAccountMapper.toDto(userAccountDoc);
        assertEquals("nishant@gmail.com", userAccountDto.getEmailId());
        assertEquals("nishant", userAccountDto.getPassword());
    }

    @Test
    void toDoc() {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setEmailId("priya@gmail.com");
        userAccountDto.setPassword("priya");

        UserAccountDoc userAccountDoc = userAccountMapper.toDoc(userAccountDto);
        assertEquals("priya@gmail.com", userAccountDoc.getEmailId());
        assertEquals("priya", userAccountDoc.getPassword());
    }
}