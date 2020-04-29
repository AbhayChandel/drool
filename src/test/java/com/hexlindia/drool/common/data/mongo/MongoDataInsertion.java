package com.hexlindia.drool.common.data.mongo;

import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.doc.UserProfileDoc;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MongoDataInsertion {

    public void insertData() {
        insertUserData();
    }

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ObjectId insertUserData() {
        UserAccountDoc userAccountDocPriyanka = new UserAccountDoc();
        userAccountDocPriyanka.setEmailId("priyanka.singh@gmail.com");
        userAccountDocPriyanka.setPassword(passwordEncoder.encode("priyanka"));
        userAccountDocPriyanka.setActive(true);
        mongoOperations.save(userAccountDocPriyanka);
        ObjectId id = userAccountDocPriyanka.getId();

        UserProfileDoc userProfileDoc = new UserProfileDoc();
        userProfileDoc.setId(id);
        userProfileDoc.setCity("Bilaspur");
        userProfileDoc.setGender("F");
        userProfileDoc.setMobile("9876543210");
        userProfileDoc.setName("Eshika Sharama");
        userProfileDoc.setUsername("EshikaLove");
        mongoOperations.save(userProfileDoc);

        System.out.println("User data inserted successfully");
        return id;
    }

    public VideoDoc inserVideoData(ObjectId userId) {
        ProductRef productRefDto1 = new ProductRef();
        productRefDto1.setId("p123");
        productRefDto1.setName("Tom Ford Vetiver");
        productRefDto1.setType("Fragrance");
        ProductRef productRefDto2 = new ProductRef();
        productRefDto2.setId("p456");
        productRefDto2.setName("Tom Ford Black");
        productRefDto2.setType("Fragrance");
        List<ProductRef> productRefDtoList = new ArrayList<>();
        productRefDtoList.add(productRefDto1);
        productRefDtoList.add(productRefDto2);
        UserRef UserRef = new UserRef();
        UserRef.setId(userId);
        UserRef.setUsername("user123");
        VideoDoc videoDoc = new VideoDoc();
        videoDoc.setType(PostType.review);
        videoDoc.setTitle("Added as part of test setup");
        videoDoc.setDescription("This is an honest review of Tom Ford Vetiver");
        videoDoc.setSourceId("s123");
        videoDoc.setProductRefList(productRefDtoList);
        videoDoc.setUserRef(UserRef);

        VideoComment videoComment1 = new VideoComment();
        videoComment1.setUserRef(new UserRef(userId, "username1"));
        videoComment1.setComment("This is first dummy comment");
        videoComment1.setDatePosted(LocalDateTime.now().minusDays(2));
        videoComment1.setLikes(20);

        VideoComment videoComment2 = new VideoComment();
        videoComment2.setUserRef(new UserRef(userId, "username1"));
        videoComment2.setComment("This is second dummy comment");
        videoComment2.setDatePosted(LocalDateTime.now().minusDays(1));
        videoComment2.setLikes(10);

        videoDoc.setCommentList(Arrays.asList(videoComment1, videoComment2));
        videoDoc.setDatePosted(LocalDateTime.now().minusDays(3));
        videoDoc.setActive(true);
        mongoOperations.save(videoDoc);

        System.out.println("Video data inserted successfully");
        return videoDoc;
    }
}
