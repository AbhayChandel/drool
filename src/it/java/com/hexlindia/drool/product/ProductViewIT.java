package com.hexlindia.drool.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.product.data.doc.AspectOption;
import com.hexlindia.drool.product.data.doc.AspectResultDoc;
import com.hexlindia.drool.product.data.doc.AspectsDoc;
import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.dto.ProductPageDto;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ProductViewIT {

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

    private Map<String, ObjectId> insertedProducts = new HashMap<>();

    private String getfindProductPageViewByIdUri() {
        return "/" + restUriVersion + "/view/product/page/id";
    }

    @BeforeEach
    void setup() {
        AspectResultDoc aspectStyle = new AspectResultDoc("1", "pc", "Top Styles", 45,
                Arrays.asList(new AspectOption("Bohemian", 5)));
        AspectResultDoc aspectOccasion = new AspectResultDoc("2", "pc", "Occasion", 35,
                Arrays.asList(new AspectOption("Wedding", 15)));
        ProductDoc productDocActive = new ProductDoc();
        AspectsDoc aspectsDoc = new AspectsDoc();
        aspectsDoc.setAspectResultDocList(Arrays.asList(aspectStyle, aspectOccasion));
        aspectsDoc.setExternalAspectIds(Arrays.asList(new ObjectId(), new ObjectId(), new ObjectId()));
        productDocActive.setAspectsDoc(aspectsDoc);
        productDocActive.setName("Lakme 9 to 5");
        productDocActive.setActive(true);
        this.mongoOperations.save(productDocActive);
        insertedProducts.put("active", productDocActive.getId());
        ProductDoc productDocInactive = new ProductDoc();
        productDocInactive.setAspectsDoc(aspectsDoc);
        productDocInactive.setName("L' Oreal Collosal Kajal");
        productDocInactive.setActive(false);
        this.mongoOperations.save(productDocInactive);
        insertedProducts.put("inactive", productDocInactive.getId());
    }

    @Test
    void testGetProductPageById() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ProductPageDto> responseEntity = restTemplate.exchange(getfindProductPageViewByIdUri() + "/" + insertedProducts.get("active"), HttpMethod.GET, httpEntity, ProductPageDto.class);
        ProductPageDto productPageDto = responseEntity.getBody();
        assertNotNull(productPageDto);
        assertNotNull(productPageDto.getProductDto().getId());
        assertTrue(productPageDto.getProductDto().getActive());
        assertEquals("Lakme 9 to 5", productPageDto.getProductDto().getName());
        assertEquals("1", productPageDto.getProductDto().getAspectResults().get(0).getId());
        assertEquals("pc", productPageDto.getProductDto().getAspectResults().get(0).getDisplayComponent());
        assertEquals("Top Styles", productPageDto.getProductDto().getAspectResults().get(0).getTitle());
        assertEquals(45, productPageDto.getProductDto().getAspectResults().get(0).getVotes());
        assertEquals("Bohemian", productPageDto.getProductDto().getAspectResults().get(0).getOptions().get(0).getName());
        assertEquals(5, productPageDto.getProductDto().getAspectResults().get(0).getOptions().get(0).getVotes());
        assertEquals("Wedding", productPageDto.getProductDto().getAspectResults().get(1).getOptions().get(0).getName());
        assertEquals(15, productPageDto.getProductDto().getAspectResults().get(1).getOptions().get(0).getVotes());
    }


}
