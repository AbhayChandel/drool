package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.data.doc.UserProfileDoc;
import com.hexlindia.drool.user.dto.UserProfileDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserProfileMapperTest {

    @Autowired
    UserProfileMapper userProfileMapper;

    @Test
    void toDoc() {
        UserProfileDto userProfileDto = new UserProfileDto();
        ObjectId accountId = new ObjectId();
        userProfileDto.setId(accountId.toHexString());
        userProfileDto.setCity("Chandigarh");
        userProfileDto.setGender("M");
        userProfileDto.setMobile("9876543210");
        userProfileDto.setName("Ajay Singh");
        userProfileDto.setUsername("Ajayboss");

        UserProfileDoc userProfileDoc = userProfileMapper.toDoc(userProfileDto);
        assertEquals(accountId, userProfileDoc.getId());
        assertEquals("Chandigarh", userProfileDoc.getCity());
        assertEquals("M", userProfileDoc.getGender());
        assertEquals("9876543210", userProfileDoc.getMobile());
        assertEquals("Ajay Singh", userProfileDoc.getName());
        assertEquals("Ajayboss", userProfileDoc.getUsername());
    }

    @Test
    void toDto() {
        ObjectId id = new ObjectId();
        UserProfileDoc userProfileDoc = new UserProfileDoc();
        userProfileDoc.setId(id);
        userProfileDoc.setUsername("shilpa11");
        userProfileDoc.setName("shilpa singh");
        userProfileDoc.setGender("F");
        userProfileDoc.setCity("Indore");
        userProfileDoc.setMobile("12345678901");

        UserProfileDto userProfileDto = userProfileMapper.toDto(userProfileDoc);
        assertEquals(id.toHexString(), userProfileDto.getId());
        assertEquals("Indore", userProfileDto.getCity());
        assertEquals("F", userProfileDto.getGender());
        assertEquals("12345678901", userProfileDto.getMobile());
        assertEquals("shilpa singh", userProfileDto.getName());
        assertEquals("shilpa11", userProfileDto.getUsername());
    }
}