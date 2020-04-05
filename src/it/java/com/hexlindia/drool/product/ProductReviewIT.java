package com.hexlindia.drool.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.config.MongoDBConfig;
import com.hexlindia.drool.product.data.doc.*;
import com.hexlindia.drool.product.dto.AspectTemplateDto;
import com.hexlindia.drool.product.dto.ReviewDialogFormsDto;
import com.hexlindia.drool.product.dto.ReviewDto;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(MongoDBConfig.class)
@Slf4j
public class ProductReviewIT {

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

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getSaveUri() {
        return "/" + restUriVersion + "/product/review/save";
    }

    private String getAspectTemplatesUri() {
        return "/" + restUriVersion + "/product/review/forms";
    }

    private Map<String, ObjectId> insertedProducts = new HashMap<>();
    private List<ObjectId> insertedAspectTemplates = new ArrayList<>();
    private List<ObjectId> insertedBrands = new ArrayList<>();

    private String authToken;

    @BeforeEach
    void setup() throws JSONException, JsonProcessingException {
        getAuthToken();
        insertAspectTemplates();
        insertProducts();
        insertBrands();
    }

    @Test
    void testGetReviewForms() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ReviewDialogFormsDto> responseEntity = restTemplate.exchange(getAspectTemplatesUri() + "/" + insertedProducts.get("active") + "/" + insertedBrands.get(0), HttpMethod.GET, httpEntity, ReviewDialogFormsDto.class);
        List<AspectTemplateDto> aspectTemplates = responseEntity.getBody().getAspectTemplateDtoList();
        assertEquals(3, aspectTemplates.size());
        assertEquals("Occasions", aspectTemplates.get(0).getTitle());
        assertEquals(4, aspectTemplates.get(0).getOptions().size());
        assertEquals("Style", aspectTemplates.get(1).getTitle());
        assertEquals(4, aspectTemplates.get(1).getOptions().size());
        assertNotNull(aspectTemplates.get(2).getId());
        assertEquals("Shades", aspectTemplates.get(2).getTitle());
        assertEquals(3, aspectTemplates.get(2).getOptions().size());
        List<String> ratingMetrics = responseEntity.getBody().getBrandRatingMetrics();
        assertNotNull(ratingMetrics);
        assertEquals(5, ratingMetrics.size());

    }

    @Test
    void testSaveTextReview() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        JSONObject reviewDto = new JSONObject();
        reviewDto.put("reviewType", "text");

        setAspectVotingResults(reviewDto);
        setBrandRatingResults(reviewDto);


        reviewDto.put("recommendation", "1");

        JSONObject productRefDto1 = new JSONObject();
        productRefDto1.put("id", insertedProducts.get("active").toHexString());
        productRefDto1.put("name", "Tom Ford Vetiver");
        productRefDto1.put("type", "Fragrance");

        reviewDto.put("product", productRefDto1);

        JSONObject textReview = new JSONObject();
        textReview.put("detailedReview", "THis is a details text review");
        textReview.put("reviewSummary", "This is text review summary");

        reviewDto.put("textReview", textReview);
        reviewDto.put("videoReview", new JSONObject());

        JSONObject UserRefDto = new JSONObject();
        UserRefDto.put("id", "u123");
        UserRefDto.put("username", "user123");

        reviewDto.put("user", UserRefDto);

        HttpEntity<String> request = new HttpEntity<>(reviewDto.toString(), headers);
        ResponseEntity<ReviewDto> responseEntity = this.restTemplate.postForEntity(getSaveUri(), request, ReviewDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ReviewDto reviewDtoReturned = responseEntity.getBody();
        assertNotNull(reviewDtoReturned.getId());


    }

    @Test
    void testVideoReviewSave() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        JSONObject reviewDto = new JSONObject();
        reviewDto.put("reviewType", "video");

        JSONObject aspectPreferenceDtoStyle = new JSONObject();
        aspectPreferenceDtoStyle.put("id", "abc");
        JSONArray aspectPreferenceDtoStyleSelected = new JSONArray();
        aspectPreferenceDtoStyleSelected.put("Retro");
        aspectPreferenceDtoStyleSelected.put("Bohemian");
        aspectPreferenceDtoStyle.put("selected", aspectPreferenceDtoStyleSelected);

        JSONObject aspectPreferenceDtoOcassion = new JSONObject();
        aspectPreferenceDtoOcassion.put("id", "abc");
        JSONArray aspectPreferenceDtoOcassionSelected = new JSONArray();
        aspectPreferenceDtoOcassionSelected.put("Wedding");
        aspectPreferenceDtoOcassionSelected.put("Cocktail");
        aspectPreferenceDtoOcassion.put("selected", aspectPreferenceDtoOcassionSelected);

        JSONArray aspectPreferenceDtoArray = new JSONArray();
        aspectPreferenceDtoArray.put(aspectPreferenceDtoStyle);
        aspectPreferenceDtoArray.put(aspectPreferenceDtoOcassion);

        reviewDto.put("aspects", aspectPreferenceDtoArray);

        JSONObject brandRatingDtoTrendy = new JSONObject();
        brandRatingDtoTrendy.put("name", "Trendy");
        brandRatingDtoTrendy.put("rating", 4);
        JSONObject brandRatingDtoTrustable = new JSONObject();
        brandRatingDtoTrustable.put("name", "Trustable");
        brandRatingDtoTrustable.put("rating", 2);
        JSONArray brandRatingDtoArray = new JSONArray();
        brandRatingDtoArray.put(brandRatingDtoTrendy);

        JSONObject brandRef = new JSONObject();
        brandRef.put("id", ObjectId.get().toHexString());
        brandRef.put("name", "Lakme");

        JSONObject brandCriteriaRatingsDetails = new JSONObject();
        brandCriteriaRatingsDetails.put("brandCriteriaRatings", brandRatingDtoArray);
        brandCriteriaRatingsDetails.put("brandRef", brandRef);
        reviewDto.put("brandCriteriaRatingsDetails", brandCriteriaRatingsDetails);

        reviewDto.put("recommendation", "1");

        JSONObject productRefDto1 = new JSONObject();
        productRefDto1.put("id", insertedProducts.get("active").toHexString());
        productRefDto1.put("name", "Tom Ford Vetiver");
        productRefDto1.put("type", "Fragrance");

        reviewDto.put("product", productRefDto1);

        JSONObject videoReview = new JSONObject();
        videoReview.put("type", "review");
        videoReview.put("active", "true");
        videoReview.put("title", "Review for Tom Ford Vetiver");
        videoReview.put("description", "This is an honest review of Tom Ford Vetiver");
        videoReview.put("sourceId", "s123");

        reviewDto.put("textReview", new JSONObject());
        reviewDto.put("videoReview", videoReview);

        JSONObject UserRefDto = new JSONObject();
        UserRefDto.put("id", "u123");
        UserRefDto.put("username", "user123");

        reviewDto.put("user", UserRefDto);

        HttpEntity<String> request = new HttpEntity<>(reviewDto.toString(), headers);
        ResponseEntity<ReviewDto> responseEntity = this.restTemplate.postForEntity(getSaveUri(), request, ReviewDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ReviewDto reviewDtoReturned = responseEntity.getBody();
        assertNotNull(reviewDtoReturned.getId());


    }

    private void setAspectVotingResults(JSONObject reviewDto) throws JSONException {
        JSONObject aspectPreferenceDtoStyle = new JSONObject();
        aspectPreferenceDtoStyle.put("id", "1");
        JSONArray aspectPreferenceDtoStyleSelected = new JSONArray();
        aspectPreferenceDtoStyleSelected.put("Chic");
        aspectPreferenceDtoStyleSelected.put("Casual");
        aspectPreferenceDtoStyle.put("selected", aspectPreferenceDtoStyleSelected);

        JSONObject aspectPreferenceDtoOcassion = new JSONObject();
        aspectPreferenceDtoOcassion.put("id", "2");
        JSONArray aspectPreferenceDtoOcassionSelected = new JSONArray();
        aspectPreferenceDtoOcassionSelected.put("Cocktail");
        aspectPreferenceDtoOcassion.put("selected", aspectPreferenceDtoOcassionSelected);

        JSONArray aspectPreferenceDtoArray = new JSONArray();
        aspectPreferenceDtoArray.put(aspectPreferenceDtoStyle);
        aspectPreferenceDtoArray.put(aspectPreferenceDtoOcassion);

        reviewDto.put("aspects", aspectPreferenceDtoArray);
    }

    private void setBrandRatingResults(JSONObject reviewDto) throws JSONException {
        JSONObject brandRatingDtoTrendy = new JSONObject();
        brandRatingDtoTrendy.put("name", "Trendy");
        brandRatingDtoTrendy.put("rating", 4);
        JSONObject brandRatingDtoTrustable = new JSONObject();
        brandRatingDtoTrustable.put("name", "Trustable");
        brandRatingDtoTrustable.put("rating", 2);
        JSONArray brandRatingDtoArray = new JSONArray();
        brandRatingDtoArray.put(brandRatingDtoTrendy);
        brandRatingDtoArray.put(brandRatingDtoTrustable);

        JSONObject brandRef = new JSONObject();
        brandRef.put("id", ObjectId.get().toHexString());
        brandRef.put("name", "Lakme");

        JSONObject brandCriteriaRatingsDetails = new JSONObject();
        brandCriteriaRatingsDetails.put("brandCriteriaRatings", brandRatingDtoArray);
        brandCriteriaRatingsDetails.put("brandRef", brandRef);
        reviewDto.put("brandCriteriaRatingsDetails", brandCriteriaRatingsDetails);
    }


    private void getAuthToken() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jwtRequestJson = new JSONObject();
        jwtRequestJson.put("email", "talk_to_priyanka@gmail.com");
        jwtRequestJson.put("password", "priyanka");
        HttpEntity<String> request = new HttpEntity<>(jwtRequestJson.toString(), headers);
        String response = this.restTemplate.postForEntity(getAuthenticationUri(), request, String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        authToken = rootNode.path("authToken").asText();
    }

    private void insertProducts() {
        ProductDoc productDocActive = new ProductDoc();
        productDocActive.setName("Lakme 9 to 5");
        productDocActive.setActive(true);
        setAspectDoc(productDocActive);
        this.mongoOperations.save(productDocActive);
        insertedProducts.put("active", productDocActive.getId());
        ProductDoc productDocInactive = new ProductDoc();
        productDocInactive.setName("L' Oreal Collosal Kajal");
        productDocInactive.setActive(false);
        this.mongoOperations.save(productDocInactive);
        insertedProducts.put("inactive", productDocInactive.getId());
    }

    private void setAspectDoc(ProductDoc productDoc) {
        AspectResultDoc aspectStyle = new AspectResultDoc("1", "pc", "Top Styles", 75,
                Arrays.asList(new AspectOption("Retro", 25), new AspectOption("Chic", 55),
                        new AspectOption("Bohemian", 5), new AspectOption("Casual", 45)));
        AspectResultDoc aspectOccasion = new AspectResultDoc("2", "pc", "Occasion", 65,
                Arrays.asList(new AspectOption("Wedding", 5), new AspectOption("Clubbing", 35), new AspectOption("Cocktail", 25)));

        AspectsDoc aspectsDoc = new AspectsDoc();
        aspectsDoc.setAspectResultDocList(Arrays.asList(aspectStyle, aspectOccasion));
        aspectsDoc.setExternalAspectIds(insertedAspectTemplates);
        aspectsDoc.setInternalAspects(getInternalAspects());

        productDoc.setAspectsDoc(aspectsDoc);
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

    private void insertBrands() {
        List<String> brandRatingMetrics = new ArrayList<>();
        brandRatingMetrics.add("Trustable");
        brandRatingMetrics.add("Affordable");
        brandRatingMetrics.add("Trendy");
        brandRatingMetrics.add("Quality");
        brandRatingMetrics.add("Overall");
        BrandDoc brandDoc = new BrandDoc();
        brandDoc.setRatingMetrics(brandRatingMetrics);
        brandDoc.setName("Maybelline");
        brandDoc.setActive(true);
        mongoOperations.save(brandDoc);
        insertedBrands.add(brandDoc.getId());
    }
}
