package com.hexlindia.drool.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.product.data.doc.ProductDoc;
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
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    private Map<String, ObjectId> insertedProducts = new HashMap<>();

    private String authToken;

    @BeforeEach
    void setup() throws JSONException, JsonProcessingException {
        getAuthToken();
        insertProducts();
    }

    @Test
    void testTextReviewSave() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        JSONObject reviewDto = new JSONObject();
        reviewDto.put("reviewType", "text");

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
        JSONArray brandRatingDtoArray = new JSONArray();
        brandRatingDtoArray.put(brandRatingDtoTrendy);

        reviewDto.put("brandRating", brandRatingDtoArray);
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
        this.mongoOperations.save(productDocActive);
        insertedProducts.put("active", productDocActive.getId());
        ProductDoc productDocInactive = new ProductDoc();
        productDocInactive.setName("L' Oreal Collosal Kajal");
        productDocInactive.setActive(false);
        this.mongoOperations.save(productDocInactive);
        insertedProducts.put("inactive", productDocInactive.getId());
    }
}
