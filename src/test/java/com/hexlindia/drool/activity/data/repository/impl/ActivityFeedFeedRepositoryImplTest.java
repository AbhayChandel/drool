package com.hexlindia.drool.activity.data.repository.impl;

import com.hexlindia.drool.activity.FeedDocFields;
import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.activity.data.repository.api.ActivityFeedRepository;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActivityFeedFeedRepositoryImplTest {

    @Autowired
    ActivityFeedRepository activityFeedRepository;

    @Autowired
    MongoOperations mongoOperations;

    ObjectId insertedPostId = ObjectId.get();

    @Test
    void testSave() {
        FeedDoc feedDoc = new FeedDoc();
        feedDoc.setPostId(ObjectId.get());
        feedDoc.setPostType("guide");
        feedDoc.setPostMedium("video");
        feedDoc.setTitle(("Test guide for repository testing"));
        feedDoc.setSourceId("ax4n6k5xk");
        feedDoc.setDatePosted(LocalDateTime.now());
        feedDoc.setLikes("0");
        feedDoc.setViews("0");
        feedDoc.setComments(0);
        feedDoc.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme 9to5", "lipstick"), new ProductRef("2", "Maybelline Collosal Kajal", "kajal")));
        feedDoc.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));

        assertNotNull(activityFeedRepository.save(feedDoc));
    }

    @Test
    void testGetFeed() {
        List<FeedDoc> feedDocList = activityFeedRepository.getFeed(0);
        assertEquals(10, feedDocList.size());
        boolean isFirstDoc = true;
        FeedDoc previousFeedDoc = null;
        for (FeedDoc feedDoc : feedDocList) {
            if (isFirstDoc) {
                previousFeedDoc = feedDoc;
                isFirstDoc = false;
                continue;
            }
            assertTrue(feedDoc.getDatePosted().isBefore(previousFeedDoc.getDatePosted()));
            previousFeedDoc = feedDoc;
        }
    }

    @Test
    void testSetLikes() {
        assertEquals("201", activityFeedRepository.setField(insertedPostId, FeedDocFields.likes, "201").getLikes());
    }

    @Test
    void testSetViews() {
        assertEquals("301", activityFeedRepository.setField(insertedPostId, FeedDocFields.views, "301").getViews());
    }

    @Test
    void testIncrementComments() {
        assertEquals(401, activityFeedRepository.incrementDecrementField(insertedPostId, FeedDocFields.comments, 1).getComments());
    }

    @Test
    void testDecrementComments() {
        assertEquals(399, activityFeedRepository.incrementDecrementField(insertedPostId, FeedDocFields.comments, -1).getComments());
    }


    @BeforeEach
    void setup() {
        FeedDoc feedDoc = new FeedDoc();
        feedDoc.setPostId(insertedPostId);
        feedDoc.setPostType("guide");
        feedDoc.setPostMedium("video");
        feedDoc.setTitle(("Test guide for repository testing setup"));
        feedDoc.setSourceId("ax4n6k5xk");
        feedDoc.setDatePosted(LocalDateTime.now());
        feedDoc.setLikes("200");
        feedDoc.setViews("300");
        feedDoc.setComments(400);
        feedDoc.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme 9to5", "lipstick"), new ProductRef("2", "Maybelline Collosal Kajal", "kajal")));
        feedDoc.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDoc);

        FeedDoc feedDocLakmeFoundation = new FeedDoc();
        feedDocLakmeFoundation.setPostId(ObjectId.get());
        feedDocLakmeFoundation.setPostType("guide");
        feedDocLakmeFoundation.setPostMedium("video");
        feedDocLakmeFoundation.setTitle(("How To Apply Lakme Perfecting Liquid Foundation || How I Make It Full Coverage"));
        feedDocLakmeFoundation.setSourceId("QW46ldTDiBY");
        feedDocLakmeFoundation.setDatePosted(LocalDateTime.now().minusHours(2));
        feedDocLakmeFoundation.setLikes("34k");
        feedDocLakmeFoundation.setViews("4.3M");
        feedDocLakmeFoundation.setComments(580);
        feedDocLakmeFoundation.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme Foundation", "foundation")));
        feedDocLakmeFoundation.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocLakmeFoundation);

        FeedDoc feedDocEasyNudeMakeup = new FeedDoc();
        feedDocEasyNudeMakeup.setPostId(ObjectId.get());
        feedDocEasyNudeMakeup.setPostType("guide");
        feedDocEasyNudeMakeup.setPostMedium("video");
        feedDocEasyNudeMakeup.setTitle(("Easy Nude Makeup look | My Go-to Sunday look | Malvika Sitlani"));
        feedDocEasyNudeMakeup.setSourceId("BrRViqbD0Mw");
        feedDocEasyNudeMakeup.setDatePosted(LocalDateTime.now().minusDays(2));
        feedDocEasyNudeMakeup.setLikes("44k");
        feedDocEasyNudeMakeup.setViews("1.5M");
        feedDocEasyNudeMakeup.setComments(1099);
        feedDocEasyNudeMakeup.setProductRefList(Arrays.asList(new ProductRef("1", "Clinique FOundation", "foundation")));
        feedDocEasyNudeMakeup.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocEasyNudeMakeup);

        FeedDoc feedDocHonestReviewOfKay = new FeedDoc();
        feedDocHonestReviewOfKay.setPostId(ObjectId.get());
        feedDocHonestReviewOfKay.setPostType("review");
        feedDocHonestReviewOfKay.setPostMedium("video");
        feedDocHonestReviewOfKay.setTitle(("HONEST Review of KAY By Katrina Kaif Beauty! Swatches included"));
        feedDocHonestReviewOfKay.setSourceId("GEvx9fwkehA");
        feedDocHonestReviewOfKay.setDatePosted(LocalDateTime.now().minusMonths(1));
        feedDocHonestReviewOfKay.setLikes("2.9k");
        feedDocHonestReviewOfKay.setViews("131k");
        feedDocHonestReviewOfKay.setComments(196);
        feedDocHonestReviewOfKay.setProductRefList(Arrays.asList(new ProductRef("1", "KAY products", "products")));
        feedDocHonestReviewOfKay.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocHonestReviewOfKay);

        FeedDoc feedDocBeforeBuyKay = new FeedDoc();
        feedDocBeforeBuyKay.setPostId(ObjectId.get());
        feedDocBeforeBuyKay.setPostType("review");
        feedDocBeforeBuyKay.setPostMedium("video");
        feedDocBeforeBuyKay.setTitle(("WATCH THIS BEFORE BUYING KayByKatrina!! | Honest review + swatches of all products | Manasi Mau"));
        feedDocBeforeBuyKay.setSourceId("11QYh6RIW50");
        feedDocBeforeBuyKay.setDatePosted(LocalDateTime.now().minusDays(6));
        feedDocBeforeBuyKay.setLikes("2.4k");
        feedDocBeforeBuyKay.setViews("70.5k");
        feedDocBeforeBuyKay.setComments(125);
        feedDocBeforeBuyKay.setProductRefList(Arrays.asList(new ProductRef("1", "KAY products", "products")));
        feedDocBeforeBuyKay.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocBeforeBuyKay);

        FeedDoc feedDocBlueHeavenCandy = new FeedDoc();
        feedDocBlueHeavenCandy.setPostId(ObjectId.get());
        feedDocBlueHeavenCandy.setPostType("review");
        feedDocBlueHeavenCandy.setPostMedium("video");
        feedDocBlueHeavenCandy.setTitle(("Blue Heaven Candy Lip Colour Review "));
        feedDocBlueHeavenCandy.setSourceId("EW_1tWFIbyo");
        feedDocBlueHeavenCandy.setDatePosted(LocalDateTime.now().minusWeeks(2));
        feedDocBlueHeavenCandy.setLikes("0");
        feedDocBlueHeavenCandy.setViews("4.9k");
        feedDocBlueHeavenCandy.setComments(18);
        feedDocBlueHeavenCandy.setProductRefList(Arrays.asList(new ProductRef("1", "Candy lip color", "products")));
        feedDocBlueHeavenCandy.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocBlueHeavenCandy);

        FeedDoc feedDocBlueHeavenMatteLipstick = new FeedDoc();
        feedDocBlueHeavenMatteLipstick.setPostId(ObjectId.get());
        feedDocBlueHeavenMatteLipstick.setPostType("review");
        feedDocBlueHeavenMatteLipstick.setPostMedium("video");
        feedDocBlueHeavenMatteLipstick.setTitle(("Blue Heaven Saphire Matte Liquid Lipsticks Review & Swtaches | All 12 Shades | Nidhi Katiyar"));
        feedDocBlueHeavenMatteLipstick.setSourceId("bCnieuvgedM");
        feedDocBlueHeavenMatteLipstick.setDatePosted(LocalDateTime.now().minusWeeks(6));
        feedDocBlueHeavenMatteLipstick.setLikes("1.6k");
        feedDocBlueHeavenMatteLipstick.setViews("30.8k");
        feedDocBlueHeavenMatteLipstick.setComments(217);
        feedDocBlueHeavenMatteLipstick.setProductRefList(Arrays.asList(new ProductRef("1", "Blue Heaven Saphire Matte lipstick", "lipstick")));
        feedDocBlueHeavenMatteLipstick.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocBlueHeavenMatteLipstick);

        FeedDoc feedDocLakme9to5Mousse = new FeedDoc();
        feedDocLakme9to5Mousse.setPostId(ObjectId.get());
        feedDocLakme9to5Mousse.setPostType("guide");
        feedDocLakme9to5Mousse.setPostMedium("video");
        feedDocLakme9to5Mousse.setTitle(("NEW SHADES LAKME 9 TO 5 WEIGHTLESS MATTE MOUSSE LIP AND CHEEK COLOR II ALL SHADES SWATCHED II"));
        feedDocLakme9to5Mousse.setSourceId("bCnieuvgedM");
        feedDocLakme9to5Mousse.setDatePosted(LocalDateTime.now().minusMonths(4));
        feedDocLakme9to5Mousse.setLikes("7k");
        feedDocLakme9to5Mousse.setViews("616k");
        feedDocLakme9to5Mousse.setComments(362);
        feedDocLakme9to5Mousse.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme 9 to 5 matte mousse", "mousse")));
        feedDocLakme9to5Mousse.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocLakme9to5Mousse);

        FeedDoc feedDocNykaaUltralipstick = new FeedDoc();
        feedDocNykaaUltralipstick.setPostId(ObjectId.get());
        feedDocNykaaUltralipstick.setPostType("guide");
        feedDocNykaaUltralipstick.setPostMedium("video");
        feedDocNykaaUltralipstick.setTitle(("Nykaa Ultra Matte Lipstick Swatches "));
        feedDocNykaaUltralipstick.setSourceId("TmbON_M7rSE");
        feedDocNykaaUltralipstick.setDatePosted(LocalDateTime.now().minusWeeks(4));
        feedDocNykaaUltralipstick.setLikes("3k");
        feedDocNykaaUltralipstick.setViews("153k");
        feedDocNykaaUltralipstick.setComments(145);
        feedDocNykaaUltralipstick.setProductRefList(Arrays.asList(new ProductRef("1", "Nykaa Ultra LIpstick swatches", "lipstick")));
        feedDocNykaaUltralipstick.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocNykaaUltralipstick);

        FeedDoc feedDocMaybellineBoldsLisptick = new FeedDoc();
        feedDocMaybellineBoldsLisptick.setPostId(ObjectId.get());
        feedDocMaybellineBoldsLisptick.setPostType("guide");
        feedDocMaybellineBoldsLisptick.setPostMedium("video");
        feedDocMaybellineBoldsLisptick.setTitle(("Maybelline Loaded Bolds Lipstick Swatches"));
        feedDocMaybellineBoldsLisptick.setSourceId("pLvyrlR0cD4");
        feedDocMaybellineBoldsLisptick.setDatePosted(LocalDateTime.now().minusDays(7));
        feedDocMaybellineBoldsLisptick.setLikes("1.9k");
        feedDocMaybellineBoldsLisptick.setViews("146k");
        feedDocMaybellineBoldsLisptick.setComments(101);
        feedDocMaybellineBoldsLisptick.setProductRefList(Arrays.asList(new ProductRef("1", "Maybelline Loaded Bolds Lipstick", "lipstick")));
        feedDocMaybellineBoldsLisptick.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocMaybellineBoldsLisptick);

        FeedDoc feedDocSugarSuedeLipcolor = new FeedDoc();
        feedDocSugarSuedeLipcolor.setPostId(ObjectId.get());
        feedDocSugarSuedeLipcolor.setPostType("guide");
        feedDocSugarSuedeLipcolor.setPostMedium("video");
        feedDocSugarSuedeLipcolor.setTitle(("SUGAR Suede Secret Matte Lipcolor SWATCHES"));
        feedDocSugarSuedeLipcolor.setSourceId("7k27CVpoK9I");
        feedDocSugarSuedeLipcolor.setDatePosted(LocalDateTime.now().minusMonths(7));
        feedDocSugarSuedeLipcolor.setLikes("2.1k");
        feedDocSugarSuedeLipcolor.setViews("93.1k");
        feedDocSugarSuedeLipcolor.setComments(84);
        feedDocSugarSuedeLipcolor.setProductRefList(Arrays.asList(new ProductRef("1", "MSUGAR Suede Secret Matte Lipcolor", "lipcolor")));
        feedDocSugarSuedeLipcolor.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocSugarSuedeLipcolor);

        FeedDoc feedDocLakmeInstalliner = new FeedDoc();
        feedDocLakmeInstalliner.setPostId(ObjectId.get());
        feedDocLakmeInstalliner.setPostType("review");
        feedDocLakmeInstalliner.setPostMedium("video");
        feedDocLakmeInstalliner.setTitle(("Lakme INSTA LINER BLACK DEMO & REVIEW"));
        feedDocLakmeInstalliner.setSourceId("7k27CVpoK9I");
        feedDocLakmeInstalliner.setDatePosted(LocalDateTime.now().minusWeeks(7));
        feedDocLakmeInstalliner.setLikes("494");
        feedDocLakmeInstalliner.setViews("20.4k");
        feedDocLakmeInstalliner.setComments(138);
        feedDocLakmeInstalliner.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme INSTA LINER BLACK", "eyeliner")));
        feedDocLakmeInstalliner.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocLakmeInstalliner);

        FeedDoc feedDocLakmeArgainOilSerum = new FeedDoc();
        feedDocLakmeArgainOilSerum.setPostId(ObjectId.get());
        feedDocLakmeArgainOilSerum.setPostType("review");
        feedDocLakmeArgainOilSerum.setPostMedium("video");
        feedDocLakmeArgainOilSerum.setTitle(("lakme absolute argan oil serum foundation SPF 45 review & demo"));
        feedDocLakmeArgainOilSerum.setSourceId("BufzCV-mOQs");
        feedDocLakmeArgainOilSerum.setDatePosted(LocalDateTime.now().minusMonths(9));
        feedDocLakmeArgainOilSerum.setLikes("6.1k");
        feedDocLakmeArgainOilSerum.setViews("413k");
        feedDocLakmeArgainOilSerum.setComments(412);
        feedDocLakmeArgainOilSerum.setProductRefList(Arrays.asList(new ProductRef("1", "Llakme absolute argan oil serum foundation SPF 45", "foundation")));
        feedDocLakmeArgainOilSerum.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocLakmeArgainOilSerum);

        FeedDoc feedDocLakmeAbsoluteFoundation = new FeedDoc();
        feedDocLakmeAbsoluteFoundation.setPostId(ObjectId.get());
        feedDocLakmeAbsoluteFoundation.setPostType("review");
        feedDocLakmeAbsoluteFoundation.setPostMedium("video");
        feedDocLakmeAbsoluteFoundation.setTitle(("Lakme absolute mousse hydrating foundation for summers"));
        feedDocLakmeAbsoluteFoundation.setSourceId("mdH6GRiffI4");
        feedDocLakmeAbsoluteFoundation.setDatePosted(LocalDateTime.now().minusYears(2));
        feedDocLakmeAbsoluteFoundation.setLikes("1.1k");
        feedDocLakmeAbsoluteFoundation.setViews("57.7k");
        feedDocLakmeAbsoluteFoundation.setComments(203);
        feedDocLakmeAbsoluteFoundation.setProductRefList(Arrays.asList(new ProductRef("1", "LLakme absolute mousse hydrating foundation for summers", "foundation")));
        feedDocLakmeAbsoluteFoundation.setUserRef(new UserRef(ObjectId.get(), "shabanastyle"));
        this.mongoOperations.save(feedDocLakmeAbsoluteFoundation);
    }
}