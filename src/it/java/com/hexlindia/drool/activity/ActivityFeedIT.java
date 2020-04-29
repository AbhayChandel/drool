package com.hexlindia.drool.activity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.activity.dto.FeedDto;
import com.hexlindia.drool.common.config.MongoDBTestConfig;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(MongoDBTestConfig.class)
@Slf4j
public class ActivityFeedIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    MongoOperations mongoOperations;

    private String getActivityFeedUri() {
        return "/" + restUriVersion + "/view/activity/feed";
    }

    private static final String POST_TYPE_GUIDE = "guide";
    private static final String POST_TYPE_REVIEW = "review";
    private static final String POST_MEDIUM_VIDEO = "video";

    @BeforeEach
    void setup() throws JSONException, JsonProcessingException {
        insertFeeds();
    }

    @Test
    @Disabled
    void testGetFeed() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<FeedDto[]> responseEntity = restTemplate.exchange(getActivityFeedUri() + "/0", HttpMethod.GET, httpEntity, FeedDto[].class);
        assertNotNull(responseEntity.getBody());
        List<FeedDto> feedDtoList = Arrays.asList(responseEntity.getBody());
        assertEquals(10, feedDtoList.size());

        boolean isFirstDto = true;
        FeedDto previousFeedDto = null;
        for (FeedDto feedDto : feedDtoList) {
            if (isFirstDto) {
                previousFeedDto = feedDto;
                isFirstDto = false;
                continue;
            }
            assertTrue(getLocaldateTime(feedDto.getDatePosted()).isBefore(getLocaldateTime(previousFeedDto.getDatePosted())));
            previousFeedDto = feedDto;
        }
    }

    private LocalDateTime getLocaldateTime(String localDateTimeStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM, yyyy");
        return LocalDate.parse(localDateTimeStr, formatter).atStartOfDay();
    }

    private void insertFeeds() {
        FeedDoc feedDocLakmeFoundation = new FeedDoc();
        feedDocLakmeFoundation.setId(ObjectId.get());
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
        mongoOperations.save(feedDocLakmeFoundation);

        FeedDoc feedDocEasyNudeMakeup = new FeedDoc();
        feedDocEasyNudeMakeup.setId(ObjectId.get());
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
        mongoOperations.save(feedDocEasyNudeMakeup);

        FeedDoc feedDocHonestReviewOfKay = new FeedDoc();
        feedDocHonestReviewOfKay.setId(ObjectId.get());
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
        mongoOperations.save(feedDocHonestReviewOfKay);

        FeedDoc feedDocBeforeBuyKay = new FeedDoc();
        feedDocBeforeBuyKay.setId(ObjectId.get());
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
        mongoOperations.save(feedDocBeforeBuyKay);

        FeedDoc feedDocBlueHeavenCandy = new FeedDoc();
        feedDocBlueHeavenCandy.setId(ObjectId.get());
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
        mongoOperations.save(feedDocBlueHeavenCandy);

        FeedDoc feedDocBlueHeavenMatteLipstick = new FeedDoc();
        feedDocBlueHeavenMatteLipstick.setId(ObjectId.get());
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
        mongoOperations.save(feedDocBlueHeavenMatteLipstick);

        FeedDoc feedDocLakme9to5Mousse = new FeedDoc();
        feedDocLakme9to5Mousse.setId(ObjectId.get());
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
        mongoOperations.save(feedDocLakme9to5Mousse);

        FeedDoc feedDocNykaaUltralipstick = new FeedDoc();
        feedDocNykaaUltralipstick.setId(ObjectId.get());
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
        mongoOperations.save(feedDocNykaaUltralipstick);

        FeedDoc feedDocMaybellineBoldsLisptick = new FeedDoc();
        feedDocMaybellineBoldsLisptick.setId(ObjectId.get());
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
        mongoOperations.save(feedDocMaybellineBoldsLisptick);

        FeedDoc feedDocSugarSuedeLipcolor = new FeedDoc();
        feedDocSugarSuedeLipcolor.setId(ObjectId.get());
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
        mongoOperations.save(feedDocSugarSuedeLipcolor);

        FeedDoc feedDocLakmeInstalliner = new FeedDoc();
        feedDocLakmeInstalliner.setId(ObjectId.get());
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
        mongoOperations.save(feedDocLakmeInstalliner);

        FeedDoc feedDocLakmeArgainOilSerum = new FeedDoc();
        feedDocLakmeArgainOilSerum.setId(ObjectId.get());
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
        mongoOperations.save(feedDocLakmeArgainOilSerum);

        FeedDoc feedDocLakmeAbsoluteFoundation = new FeedDoc();
        feedDocLakmeAbsoluteFoundation.setId(ObjectId.get());
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
        mongoOperations.save(feedDocLakmeAbsoluteFoundation);
    }

    private UserRef getUserShabana() {
        return new UserRef(ObjectId.get(), "shabanastyle");
    }
}
