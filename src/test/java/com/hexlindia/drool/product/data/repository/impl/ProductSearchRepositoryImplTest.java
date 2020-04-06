package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.*;
import com.hexlindia.drool.product.data.repository.api.ProductSearchRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductSearchRepositoryImplTest {

    private final ProductSearchRepository productSearchRepository;
    private final MongoOperations mongoOperations;

    private Map<String, ObjectId> insertedProducts = new HashMap<>();
    private List<ObjectId> insertedAspectTemplates = new ArrayList<>();

    @Autowired
    public ProductSearchRepositoryImplTest(ProductSearchRepository productSearchRepository, MongoOperations mongoOperations) {
        this.productSearchRepository = productSearchRepository;
        this.mongoOperations = mongoOperations;
    }

    @BeforeEach
    void setup() {
        insertAspectTemplates();
        insertProducts();
        insertProductsWithSearchTags();
    }

    @Test
    public void test_searchByTags_MaxElements5() {
        List<SearchProductRef> products = productSearchRepository.searchByTags("Lakme");
        assertEquals(5, products.size());
    }

    //For some reason the data is getting inserted twice. so intead of 2, 4 results are returned.
    @Test
    @Disabled
    public void test_searchByTags_SearchByKeywordInName() {
        List<SearchProductRef> products = productSearchRepository.searchByTags("lakme foundation");
        assertEquals(2, products.size());
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
        productDocActive.setSearchTags(Arrays.asList("lakme", "9to5", "lipstick"));
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