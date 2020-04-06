package com.hexlindia.drool.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.product.data.doc.BrandRef;
import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.dto.SearchProductDto;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ProductSearchIT {

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

    private String getProductSearchByTagsdUri() {
        return "/" + restUriVersion + "/view/product/search/tags";
    }

    @BeforeEach
    void setup() throws JSONException, JsonProcessingException {
        insertProductsWithSearchTags();
    }

    @Test
    void testSearchByTags() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<SearchProductDto[]> responseEntity = restTemplate.exchange(getProductSearchByTagsdUri() + "/sindoor", HttpMethod.GET, httpEntity, SearchProductDto[].class);
        assertNotNull(responseEntity.getBody());
        assertEquals(3, responseEntity.getBody().length);
    }

    public void insertProductsWithSearchTags() {

        BrandRef biteBeauty = new BrandRef(new ObjectId(), "Bite Beauty");

        ProductDoc biteBeautyLipCrayon = new ProductDoc();
        biteBeautyLipCrayon.setBrandRef(biteBeauty);
        biteBeautyLipCrayon.setName("Power move creamy matte lip crayon");
        biteBeautyLipCrayon.setSearchTags(Arrays.asList("bite", "beauty", "power", "move", "creamy", "matte", "lip", "crayon"));
        this.mongoOperations.save(biteBeautyLipCrayon);

        BrandRef lakme = new BrandRef(new ObjectId(), "Lakme");
        ProductDoc lakmefacePowder = new ProductDoc();
        lakmefacePowder.setBrandRef(lakme);
        lakmefacePowder.setName("Face powder Soft pink");
        lakmefacePowder.setSearchTags(Arrays.asList("lakme", "face", "powder", "soft", "pink"));
        this.mongoOperations.save(lakmefacePowder);

        ProductDoc lakmeLiquidFoundationPearl = new ProductDoc();
        lakmeLiquidFoundationPearl.setBrandRef(lakme);
        lakmeLiquidFoundationPearl.setName("Liquid foundation Pearl");
        lakmeLiquidFoundationPearl.setSearchTags(Arrays.asList("lakme", "liquid", "foundation", "pearl"));
        this.mongoOperations.save(lakmeLiquidFoundationPearl);

        ProductDoc lakmeLiquidFoundationMarble = new ProductDoc();
        lakmeLiquidFoundationMarble.setBrandRef(lakme);
        lakmeLiquidFoundationMarble.setName("Liquid foundation Marble");
        lakmeLiquidFoundationMarble.setSearchTags(Arrays.asList("lakme", "liquid", "foundation", "marble"));
        this.mongoOperations.save(lakmeLiquidFoundationMarble);

        BrandRef lotus = new BrandRef(new ObjectId(), "Lotus");
        ProductDoc lotusHerbalSindoor = new ProductDoc();
        lotusHerbalSindoor.setBrandRef(lotus);
        lotusHerbalSindoor.setName("Herbals divine dew herbal sindoor");
        lotusHerbalSindoor.setSearchTags(Arrays.asList("lotus", "herbals", "divine", "dew", "herbal", "sindoor"));
        this.mongoOperations.save(lotusHerbalSindoor);

        ProductDoc lakmeJewelSindoorMaroon = new ProductDoc();
        lakmeJewelSindoorMaroon.setBrandRef(lakme);
        lakmeJewelSindoorMaroon.setName("Jewel Sindoor Maroon");
        lakmeJewelSindoorMaroon.setSearchTags(Arrays.asList("lakme", "jewel", "sindoor", "maroon"));
        this.mongoOperations.save(lakmeJewelSindoorMaroon);

        ProductDoc lakmeJewelSindoorRed = new ProductDoc();
        lakmeJewelSindoorRed.setBrandRef(lakme);
        lakmeJewelSindoorRed.setName("Jewel Sindoor Red");
        lakmeJewelSindoorRed.setSearchTags(Arrays.asList("lakme", "jewel", "sindoor", "red"));
        this.mongoOperations.save(lakmeJewelSindoorRed);

        BrandRef loreal = new BrandRef(new ObjectId(), "Loreal");
        ProductDoc lorealFoundation = new ProductDoc();
        lorealFoundation.setBrandRef(loreal);
        lorealFoundation.setName("Paris Infallible 24H fresh wear foundation");
        lorealFoundation.setSearchTags(Arrays.asList("loreal", "paris", "infallible", "24h", "fresh", "wear", "foundation"));
        this.mongoOperations.save(lorealFoundation);
    }

}
