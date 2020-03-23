package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.*;
import com.hexlindia.drool.product.data.repository.api.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryImplTest {

    private final ProductRepository productRepository;
    private final MongoOperations mongoOperations;

    private Map<String, ObjectId> insertedProducts = new HashMap<>();
    private List<ObjectId> insertedAspectTemplates = new ArrayList<>();

    @Autowired
    public ProductRepositoryImplTest(ProductRepository productRepository, MongoOperations mongoOperations) {
        this.productRepository = productRepository;
        this.mongoOperations = mongoOperations;
    }

    @BeforeEach
    void setup() {
        insertAspectTemplates();
        insertProducts();
    }

    @Test
    public void test_findByIdAndIsActive() {
        assertNotNull(productRepository.findById(insertedProducts.get("active")));
    }

    @Test
    public void test_findByIdAndIsNotActive() {
        assertNull(productRepository.findById(insertedProducts.get("inactive")));
    }

    @Test
    public void test_getAspectTemplates() {
        ProductAspectTemplates productAspectTemplates = productRepository.getAspectTemplates(insertedProducts.get("active"));
        assertEquals(insertedProducts.get("active"), productAspectTemplates.getId());
        assertEquals(3, productAspectTemplates.getAspectTemplates().size());
        assertNotNull(productAspectTemplates.getAspectTemplates().get(2).getId());
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

}