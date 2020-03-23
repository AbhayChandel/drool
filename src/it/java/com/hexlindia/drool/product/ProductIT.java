package com.hexlindia.drool.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.product.data.doc.*;
import com.hexlindia.drool.product.dto.AspectTemplateDto;
import com.hexlindia.drool.product.dto.ProductAspectTemplatesDto;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
import org.springframework.http.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ProductIT {

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

    private String getAspectTemplatesUri() {
        return "/" + restUriVersion + "/product//aspecttemplates/id";
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private Map<String, ObjectId> insertedProducts = new HashMap<>();
    private List<ObjectId> insertedAspectTemplates = new ArrayList<>();
    private String authToken;

    @BeforeEach
    void setup() throws JSONException, JsonProcessingException {
        insertAspectTemplates();
        insertProducts();
        getAuthToken();
    }

    @Test
    void testGetAspectTemplates() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ProductAspectTemplatesDto> responseEntity = restTemplate.exchange(getAspectTemplatesUri() + "/" + insertedProducts.get("active"), HttpMethod.GET, httpEntity, ProductAspectTemplatesDto.class);
        List<AspectTemplateDto> aspectTemplates = responseEntity.getBody().getAspectTemplateDtoList();
        assertEquals(3, aspectTemplates.size());
        assertEquals("Occasions", aspectTemplates.get(0).getTitle());
        assertEquals(4, aspectTemplates.get(0).getOptions().size());
        assertEquals("Style", aspectTemplates.get(1).getTitle());
        assertEquals(4, aspectTemplates.get(1).getOptions().size());
        assertNotNull(aspectTemplates.get(2).getId());
        assertEquals("Shades", aspectTemplates.get(2).getTitle());
        assertEquals(3, aspectTemplates.get(2).getOptions().size());

    }

    private void insertProducts() {
        AspectResultDoc aspectStyle = new AspectResultDoc("1", "pc", "Top Styles", 45,
                Arrays.asList(new AspectOption("Retro", 25), new AspectOption("Chic", 45),
                        new AspectOption("Bohemian", 5)));
        AspectResultDoc aspectOccasion = new AspectResultDoc("2", "pc", "Occasion", 35,
                Arrays.asList(new AspectOption("Wedding", 5), new AspectOption("Clubbing", 35)));
        ProductDoc productDocActive = new ProductDoc();
        AspectsDoc aspectsDoc = new AspectsDoc();
        aspectsDoc.setAspectResultDocList(Arrays.asList(aspectStyle, aspectOccasion));
        aspectsDoc.setExternalAspectIds(insertedAspectTemplates);
        AspectTemplate shadeVariant = new AspectTemplate();
        shadeVariant.setId(ObjectId.get());
        shadeVariant.setTitle("Shades");
        shadeVariant.setOptions(Arrays.asList("Red Coat", "Crimson Pink", "Plush Orange"));
        aspectsDoc.setInternalAspects(Arrays.asList(shadeVariant));
        productDocActive.setAspectsDoc(aspectsDoc);
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


}
