package com.hexlindia.drool.common.datamigration.test.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;

@ChangeLog
public class ActivityFeedChangeLog_3 {

    private static final String POST_TYPE_GUIDE = "guide";
    private static final String POST_TYPE_REVIEW = "review";
    private static final String POST_MEDIUM_VIDEO = "video";

    @ChangeSet(order = "005", id = "activity_feed", author = "")
    public void insertInitialProduct(MongoTemplate mongoTemplate) {
        FeedDoc feedDocHonestReviewOfKay = new FeedDoc();
        feedDocHonestReviewOfKay.setPostId(ObjectId.get());
        feedDocHonestReviewOfKay.setPostType(POST_TYPE_REVIEW);
        feedDocHonestReviewOfKay.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocHonestReviewOfKay.setTitle(("HONEST Review of KAY By Katrina Kaif Beauty! Swatches included"));
        feedDocHonestReviewOfKay.setSourceId("GEvx9fwkehA");
        feedDocHonestReviewOfKay.setDatePosted(LocalDateTime.now().minusMonths(1));
        feedDocHonestReviewOfKay.setLikes(2985);
        feedDocHonestReviewOfKay.setViews(131967);
        feedDocHonestReviewOfKay.setComments(196);
        feedDocHonestReviewOfKay.setProductRefList(Arrays.asList(new ProductRef("1", "KAY products", "products")));
        feedDocHonestReviewOfKay.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocHonestReviewOfKay);

        FeedDoc feedDocEasyNudeMakeup = new FeedDoc();
        feedDocEasyNudeMakeup.setPostId(ObjectId.get());
        feedDocEasyNudeMakeup.setPostType(POST_TYPE_GUIDE);
        feedDocEasyNudeMakeup.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocEasyNudeMakeup.setTitle(("Easy Nude Makeup look | My Go-to Sunday look | Malvika Sitlani"));
        feedDocEasyNudeMakeup.setSourceId("BrRViqbD0Mw");
        feedDocEasyNudeMakeup.setDatePosted(LocalDateTime.now().minusDays(2));
        feedDocEasyNudeMakeup.setLikes(44567);
        feedDocEasyNudeMakeup.setViews(1593563);
        feedDocEasyNudeMakeup.setComments(1099);
        feedDocEasyNudeMakeup.setProductRefList(Arrays.asList(new ProductRef("1", "Clinique FOundation", "foundation")));
        feedDocEasyNudeMakeup.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocEasyNudeMakeup);

        FeedDoc feedDocBeforeBuyKay = new FeedDoc();
        feedDocBeforeBuyKay.setPostId(ObjectId.get());
        feedDocBeforeBuyKay.setPostType(POST_TYPE_REVIEW);
        feedDocBeforeBuyKay.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocBeforeBuyKay.setTitle(("WATCH THIS BEFORE BUYING KayByKatrina!! | Honest review + swatches of all products | Manasi Mau"));
        feedDocBeforeBuyKay.setSourceId("11QYh6RIW50");
        feedDocBeforeBuyKay.setDatePosted(LocalDateTime.now().minusDays(6));
        feedDocBeforeBuyKay.setLikes(2445);
        feedDocBeforeBuyKay.setViews(70587);
        feedDocBeforeBuyKay.setComments(125);
        feedDocBeforeBuyKay.setProductRefList(Arrays.asList(new ProductRef("1", "KAY products", "products")));
        feedDocBeforeBuyKay.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocBeforeBuyKay);

        FeedDoc feedDocBlueHeavenCandy = new FeedDoc();
        feedDocBlueHeavenCandy.setPostId(ObjectId.get());
        feedDocBlueHeavenCandy.setPostType(POST_TYPE_REVIEW);
        feedDocBlueHeavenCandy.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocBlueHeavenCandy.setTitle(("Blue Heaven Candy Lip Colour Review "));
        feedDocBlueHeavenCandy.setSourceId("EW_1tWFIbyo");
        feedDocBlueHeavenCandy.setDatePosted(LocalDateTime.now().minusWeeks(2));
        feedDocBlueHeavenCandy.setLikes(0);
        feedDocBlueHeavenCandy.setViews(4987);
        feedDocBlueHeavenCandy.setComments(18);
        feedDocBlueHeavenCandy.setProductRefList(Arrays.asList(new ProductRef("1", "Candy lip color", "products")));
        feedDocBlueHeavenCandy.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocBlueHeavenCandy);

        FeedDoc feedDocBlueHeavenMatteLipstick = new FeedDoc();
        feedDocBlueHeavenMatteLipstick.setPostId(ObjectId.get());
        feedDocBlueHeavenMatteLipstick.setPostType(POST_TYPE_REVIEW);
        feedDocBlueHeavenMatteLipstick.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocBlueHeavenMatteLipstick.setTitle(("Blue Heaven Saphire Matte Liquid Lipsticks Review & Swtaches | All 12 Shades | Nidhi Katiyar"));
        feedDocBlueHeavenMatteLipstick.setSourceId("bCnieuvgedM");
        feedDocBlueHeavenMatteLipstick.setDatePosted(LocalDateTime.now().minusWeeks(6));
        feedDocBlueHeavenMatteLipstick.setLikes(1687);
        feedDocBlueHeavenMatteLipstick.setViews(30857);
        feedDocBlueHeavenMatteLipstick.setComments(217);
        feedDocBlueHeavenMatteLipstick.setProductRefList(Arrays.asList(new ProductRef("1", "Blue Heaven Saphire Matte lipstick", "lipstick")));
        feedDocBlueHeavenMatteLipstick.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocBlueHeavenMatteLipstick);

        FeedDoc feedDocLakme9to5Mousse = new FeedDoc();
        feedDocLakme9to5Mousse.setPostId(ObjectId.get());
        feedDocLakme9to5Mousse.setPostType(POST_TYPE_GUIDE);
        feedDocLakme9to5Mousse.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocLakme9to5Mousse.setTitle(("NEW SHADES LAKME 9 TO 5 WEIGHTLESS MATTE MOUSSE LIP AND CHEEK COLOR II ALL SHADES SWATCHED II"));
        feedDocLakme9to5Mousse.setSourceId("bCnieuvgedM");
        feedDocLakme9to5Mousse.setDatePosted(LocalDateTime.now().minusMonths(4));
        feedDocLakme9to5Mousse.setLikes(7000);
        feedDocLakme9to5Mousse.setViews(616978);
        feedDocLakme9to5Mousse.setComments(362);
        feedDocLakme9to5Mousse.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme 9 to 5 matte mousse", "mousse")));
        feedDocLakme9to5Mousse.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocLakme9to5Mousse);

        FeedDoc feedDocNykaaUltralipstick = new FeedDoc();
        feedDocNykaaUltralipstick.setPostId(ObjectId.get());
        feedDocNykaaUltralipstick.setPostType(POST_TYPE_GUIDE);
        feedDocNykaaUltralipstick.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocNykaaUltralipstick.setTitle(("Nykaa Ultra Matte Lipstick Swatches "));
        feedDocNykaaUltralipstick.setSourceId("TmbON_M7rSE");
        feedDocNykaaUltralipstick.setDatePosted(LocalDateTime.now().minusWeeks(4));
        feedDocNykaaUltralipstick.setLikes(3000);
        feedDocNykaaUltralipstick.setViews(153465);
        feedDocNykaaUltralipstick.setComments(145);
        feedDocNykaaUltralipstick.setProductRefList(Arrays.asList(new ProductRef("1", "Nykaa Ultra LIpstick swatches", "lipstick")));
        feedDocNykaaUltralipstick.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocNykaaUltralipstick);

        FeedDoc feedDocMaybellineBoldsLisptick = new FeedDoc();
        feedDocMaybellineBoldsLisptick.setPostId(ObjectId.get());
        feedDocMaybellineBoldsLisptick.setPostType(POST_TYPE_GUIDE);
        feedDocMaybellineBoldsLisptick.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocMaybellineBoldsLisptick.setTitle(("Maybelline Loaded Bolds Lipstick Swatches"));
        feedDocMaybellineBoldsLisptick.setSourceId("pLvyrlR0cD4");
        feedDocMaybellineBoldsLisptick.setDatePosted(LocalDateTime.now().minusDays(7));
        feedDocMaybellineBoldsLisptick.setLikes(1900);
        feedDocMaybellineBoldsLisptick.setViews(146675);
        feedDocMaybellineBoldsLisptick.setComments(101);
        feedDocMaybellineBoldsLisptick.setProductRefList(Arrays.asList(new ProductRef("1", "Maybelline Loaded Bolds Lipstick", "lipstick")));
        feedDocMaybellineBoldsLisptick.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocMaybellineBoldsLisptick);

        FeedDoc feedDocSugarSuedeLipcolor = new FeedDoc();
        feedDocSugarSuedeLipcolor.setPostId(ObjectId.get());
        feedDocSugarSuedeLipcolor.setPostType(POST_TYPE_GUIDE);
        feedDocSugarSuedeLipcolor.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocSugarSuedeLipcolor.setTitle(("SUGAR Suede Secret Matte Lipcolor SWATCHES"));
        feedDocSugarSuedeLipcolor.setSourceId("7k27CVpoK9I");
        feedDocSugarSuedeLipcolor.setDatePosted(LocalDateTime.now().minusMonths(7));
        feedDocSugarSuedeLipcolor.setLikes(2100);
        feedDocSugarSuedeLipcolor.setViews(93100);
        feedDocSugarSuedeLipcolor.setComments(84);
        feedDocSugarSuedeLipcolor.setProductRefList(Arrays.asList(new ProductRef("1", "MSUGAR Suede Secret Matte Lipcolor", "lipcolor")));
        feedDocSugarSuedeLipcolor.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocSugarSuedeLipcolor);

        FeedDoc feedDocLakmeInstalliner = new FeedDoc();
        feedDocLakmeInstalliner.setPostId(ObjectId.get());
        feedDocLakmeInstalliner.setPostType(POST_TYPE_REVIEW);
        feedDocLakmeInstalliner.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocLakmeInstalliner.setTitle(("Lakme INSTA LINER BLACK DEMO & REVIEW"));
        feedDocLakmeInstalliner.setSourceId("7k27CVpoK9I");
        feedDocLakmeInstalliner.setDatePosted(LocalDateTime.now().minusWeeks(7));
        feedDocLakmeInstalliner.setLikes(494);
        feedDocLakmeInstalliner.setViews(204000);
        feedDocLakmeInstalliner.setComments(138);
        feedDocLakmeInstalliner.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme INSTA LINER BLACK", "eyeliner")));
        feedDocLakmeInstalliner.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocLakmeInstalliner);

        FeedDoc feedDocLakmeArgainOilSerum = new FeedDoc();
        feedDocLakmeArgainOilSerum.setPostId(ObjectId.get());
        feedDocLakmeArgainOilSerum.setPostType(POST_TYPE_REVIEW);
        feedDocLakmeArgainOilSerum.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocLakmeArgainOilSerum.setTitle(("lakme absolute argan oil serum foundation SPF 45 review & demo"));
        feedDocLakmeArgainOilSerum.setSourceId("BufzCV-mOQs");
        feedDocLakmeArgainOilSerum.setDatePosted(LocalDateTime.now().minusMonths(9));
        feedDocLakmeArgainOilSerum.setLikes(6100);
        feedDocLakmeArgainOilSerum.setViews(413000);
        feedDocLakmeArgainOilSerum.setComments(412);
        feedDocLakmeArgainOilSerum.setProductRefList(Arrays.asList(new ProductRef("1", "Llakme absolute argan oil serum foundation SPF 45", "foundation")));
        feedDocLakmeArgainOilSerum.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocLakmeArgainOilSerum);

        FeedDoc feedDocLakmeFoundation = new FeedDoc();
        feedDocLakmeFoundation.setPostId(ObjectId.get());
        feedDocLakmeFoundation.setPostType(POST_TYPE_GUIDE);
        feedDocLakmeFoundation.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocLakmeFoundation.setTitle(("How To Apply Lakme Perfecting Liquid Foundation || How I Make It Full Coverage"));
        feedDocLakmeFoundation.setSourceId("QW46ldTDiBY");
        feedDocLakmeFoundation.setDatePosted(LocalDateTime.now().minusHours(2));
        feedDocLakmeFoundation.setLikes(34345);
        feedDocLakmeFoundation.setViews(4347654);
        feedDocLakmeFoundation.setComments(580);
        feedDocLakmeFoundation.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme Foundation", "foundation")));
        feedDocLakmeFoundation.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocLakmeFoundation);

        FeedDoc feedDocLakmeAbsoluteFoundation = new FeedDoc();
        feedDocLakmeAbsoluteFoundation.setPostId(ObjectId.get());
        feedDocLakmeAbsoluteFoundation.setPostType(POST_TYPE_REVIEW);
        feedDocLakmeAbsoluteFoundation.setPostMedium(POST_MEDIUM_VIDEO);
        feedDocLakmeAbsoluteFoundation.setTitle(("Lakme absolute mousse hydrating foundation for summers"));
        feedDocLakmeAbsoluteFoundation.setSourceId("mdH6GRiffI4");
        feedDocLakmeAbsoluteFoundation.setDatePosted(LocalDateTime.now().minusYears(2));
        feedDocLakmeAbsoluteFoundation.setLikes(1100);
        feedDocLakmeAbsoluteFoundation.setViews(57700);
        feedDocLakmeAbsoluteFoundation.setComments(203);
        feedDocLakmeAbsoluteFoundation.setProductRefList(Arrays.asList(new ProductRef("1", "LLakme absolute mousse hydrating foundation for summers", "foundation")));
        feedDocLakmeAbsoluteFoundation.setUserRef(getUserShabana());
        mongoTemplate.save(feedDocLakmeAbsoluteFoundation);

    }

    private UserRef getUserShabana() {
        return new UserRef(ObjectId.get(), "shabanastyle");
    }
}
