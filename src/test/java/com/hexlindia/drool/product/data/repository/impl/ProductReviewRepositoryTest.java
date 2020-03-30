package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.common.config.MongoDBConfig;
import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.product.business.impl.usecase.ReviewType;
import com.hexlindia.drool.product.data.doc.*;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import com.hexlindia.drool.product.dto.AspectVotingDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(MongoDBConfig.class)
class ProductReviewRepositoryTest {

    private final ProductReviewRepository productReviewRepository;
    private final MongoOperations mongoOperations;

    private Map<String, ObjectId> insertedProducts = new HashMap<>();

    @Autowired
    public ProductReviewRepositoryTest(ProductReviewRepository productReviewRepository, MongoOperations mongoOperations) {
        this.productReviewRepository = productReviewRepository;
        this.mongoOperations = mongoOperations;
    }

    @BeforeEach
    void setup() {
        insertProducts();
    }

    @Test
    void saveTextReview() {
        ReviewDoc reviewDoc = new ReviewDoc();
        reviewDoc.setReviewType(ReviewType.text);
        reviewDoc.setRecommendation("1");
        reviewDoc.setDetailedReview("This is a detailed review");
        reviewDoc.setReviewSummary("This is a review summary");
        reviewDoc.setUserRef(new UserRef("u123", "username123"));

        AspectVotingDto aspectVotingDtoStyle = new AspectVotingDto();
        aspectVotingDtoStyle.setAspectId("1");
        aspectVotingDtoStyle.setSelectedOptions(Arrays.asList("Chic", "Casual"));
        AspectVotingDto aspectVotingDtoOccasion = new AspectVotingDto();
        aspectVotingDtoOccasion.setAspectId("2");
        aspectVotingDtoOccasion.setSelectedOptions(Arrays.asList("Clubbing", "Cocktail"));

        assertNotNull(productReviewRepository.save(reviewDoc, insertedProducts.get("active"), Arrays.asList(aspectVotingDtoStyle, aspectVotingDtoOccasion)));
    }

    @Test
    void saveVideoReview() {
        ReviewDoc reviewDoc = new ReviewDoc();
        reviewDoc.setReviewType(ReviewType.video);
        reviewDoc.setRecommendation("1");
        reviewDoc.setVideoId(ObjectId.get());
        reviewDoc.setUserRef(new UserRef("u123", "username123"));
        assertNotNull(productReviewRepository.save(reviewDoc, insertedProducts.get("active"), new ArrayList<>()));
    }

    private void insertProducts() {
        ProductDoc productDocActive = new ProductDoc();
        productDocActive.setName("Lakme 9 to 5");
        productDocActive.setActive(true);
        productDocActive.setAspectsDoc(getAspectDoc());
        this.mongoOperations.save(productDocActive);
        insertedProducts.put("active", productDocActive.getId());
        ProductDoc productDocInactive = new ProductDoc();
        productDocInactive.setName("L' Oreal Collosal Kajal");
        productDocInactive.setActive(false);
        this.mongoOperations.save(productDocInactive);
        insertedProducts.put("inactive", productDocInactive.getId());
    }

    private AspectsDoc getAspectDoc() {
        AspectResultDoc aspectStyle = new AspectResultDoc("1", "pc", "Top Styles", 75,
                Arrays.asList(new AspectOption("Retro", 25), new AspectOption("Chic", 55),
                        new AspectOption("Bohemian", 5), new AspectOption("Casual", 45)));
        AspectResultDoc aspectOccasion = new AspectResultDoc("2", "pc", "Occasion", 65,
                Arrays.asList(new AspectOption("Wedding", 5), new AspectOption("Clubbing", 35), new AspectOption("Cocktail", 25)));

        AspectsDoc aspectsDoc = new AspectsDoc();
        aspectsDoc.setAspectResultDocList(Arrays.asList(aspectStyle, aspectOccasion));

        return aspectsDoc;
    }
}