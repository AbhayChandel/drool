package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.common.config.MongoDBTestConfig;
import com.hexlindia.drool.product.business.impl.usecase.ReviewType;
import com.hexlindia.drool.product.data.doc.*;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import com.hexlindia.drool.product.dto.AspectVotingDto;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(MongoDBTestConfig.class)
class ProductReviewRepositoryTest {

    private final ProductReviewRepository productReviewRepository;
    private final MongoOperations mongoOperations;

    private Map<String, ObjectId> insertedProducts = new HashMap<>();
    private List<ObjectId> insertedAspectTemplates = new ArrayList<>();

    @Autowired
    public ProductReviewRepositoryTest(ProductReviewRepository productReviewRepository, MongoOperations mongoOperations) {
        this.productReviewRepository = productReviewRepository;
        this.mongoOperations = mongoOperations;
    }

    @BeforeEach
    void setup() {
        insertAspectTemplates();
        insertProducts();

    }

    @Test
    public void test_getAspectTemplates() {
        ProductAspectTemplates productAspectTemplates = productReviewRepository.getAspectTemplates(insertedProducts.get("active"));
        assertEquals(3, productAspectTemplates.getAspectTemplates().size());
        assertNotNull(productAspectTemplates.getAspectTemplates().get(2).getId());
    }

    @Test
    void saveTextReview() {
        ReviewDoc reviewDoc = new ReviewDoc();
        reviewDoc.setReviewType(ReviewType.text);
        reviewDoc.setRecommendation("1");
        reviewDoc.setDetailedReview("This is a detailed review");
        reviewDoc.setReviewSummary("This is a review summary");
        reviewDoc.setUserRef(new UserRef(ObjectId.get(), "username123"));

        AspectVotingDto aspectVotingDtoStyle = new AspectVotingDto();
        aspectVotingDtoStyle.setAspectId(ObjectId.get().toHexString());
        aspectVotingDtoStyle.setSelectedOptions(Arrays.asList("Chic", "Casual"));
        AspectVotingDto aspectVotingDtoOccasion = new AspectVotingDto();
        aspectVotingDtoOccasion.setAspectId(ObjectId.get().toHexString());
        aspectVotingDtoOccasion.setSelectedOptions(Arrays.asList("Clubbing", "Cocktail"));

        assertNotNull(productReviewRepository.save(reviewDoc, insertedProducts.get("active"), Arrays.asList(aspectVotingDtoStyle, aspectVotingDtoOccasion)));
    }

    @Test
    void saveVideoReview() {
        ReviewDoc reviewDoc = new ReviewDoc();
        reviewDoc.setReviewType(ReviewType.video);
        reviewDoc.setRecommendation("1");
        reviewDoc.setVideoId(ObjectId.get());
        reviewDoc.setUserRef(new UserRef(ObjectId.get(), "username123"));
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
        aspectsDoc.setExternalAspectIds(insertedAspectTemplates);
        aspectsDoc.setInternalAspects(getInternalAspects());

        return aspectsDoc;
    }

    private void insertAspectTemplates() {
        AspectTemplate aspectTemplateOccasion = new AspectTemplate();
        aspectTemplateOccasion.setTitle("Occasions");
        aspectTemplateOccasion.setOptions(Arrays.asList("Wedding", "Day out", "Brunch", "Partying"));

        this.mongoOperations.save(aspectTemplateOccasion);
        insertedAspectTemplates.add(aspectTemplateOccasion.getId());

        AspectTemplate aspectTemplateStyle = new AspectTemplate();
        aspectTemplateStyle.setTitle("Style");
        aspectTemplateStyle.setOptions(Arrays.asList("Retro", "Chic", "Bohemian", "Casual"));
        this.mongoOperations.save(aspectTemplateStyle);
        insertedAspectTemplates.add(aspectTemplateStyle.getId());
    }

    private List<AspectTemplate> getInternalAspects() {
        AspectTemplate shadeVariant = new AspectTemplate();
        shadeVariant.setId(ObjectId.get());
        shadeVariant.setTitle("Shades");
        shadeVariant.setOptions(Arrays.asList("Red Coat", "Crimson Pink", "Plush Orange"));
        return Arrays.asList(shadeVariant);
    }
}