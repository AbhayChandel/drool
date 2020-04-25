package com.hexlindia.drool.common.datamigration.test.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.hexlindia.drool.product.data.doc.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ChangeLog
public class ProductCollectionChangeLog_2 {

    @ChangeSet(order = "002", id = "product", author = "")
    public void insertInitialProduct(MongoTemplate mongoTemplate) {

        AspectTemplate aspectTemplateOccasion = new AspectTemplate();
        aspectTemplateOccasion.setTitle("Occasions");
        aspectTemplateOccasion.setOptions(Arrays.asList("Wedding", "Day out", "Brunch", "Partying"));
        mongoTemplate.save(aspectTemplateOccasion);

        AspectTemplate aspectTemplateStyle = new AspectTemplate();
        aspectTemplateStyle.setTitle("Style");
        aspectTemplateStyle.setOptions(Arrays.asList("Retro", "Chic", "Bohemian", "Casual"));
        mongoTemplate.save(aspectTemplateStyle);

        AspectResultDoc aspectStyle = new AspectResultDoc(aspectTemplateStyle.getId().toHexString(), "pc", "Top Styles", 45,
                Arrays.asList(new AspectOption("Retro", 25), new AspectOption("Chic", 45),
                        new AspectOption("Bohemian", 5)));
        AspectResultDoc aspectOccasion = new AspectResultDoc(aspectTemplateOccasion.getId().toHexString(), "pc", "Occasion", 35,
                Arrays.asList(new AspectOption("Wedding", 5), new AspectOption("Clubbing", 35)));
        ProductDoc productDocActive = new ProductDoc();
        AspectsDoc aspectsDoc = new AspectsDoc();
        aspectsDoc.setAspectResultDocList(Arrays.asList(aspectStyle, aspectOccasion));
        aspectsDoc.setExternalAspectIds(Arrays.asList(aspectTemplateOccasion.getId(), aspectTemplateStyle.getId()));
        AspectTemplate shadeVariant = new AspectTemplate();
        shadeVariant.setId(ObjectId.get());
        shadeVariant.setTitle("Shades");
        shadeVariant.setOptions(Arrays.asList("Red Coat", "Crimson Pink", "Plush Orange"));
        aspectsDoc.setInternalAspects(Arrays.asList(shadeVariant));
        productDocActive.setAspectsDoc(aspectsDoc);
        productDocActive.setName("Lakme 9 to 5");
        productDocActive.setActive(true);
        mongoTemplate.save(productDocActive);
        ProductDoc productDocInactive = new ProductDoc();
        productDocInactive.setName("L' Oreal Collosal Kajal");
        productDocInactive.setActive(false);
        mongoTemplate.save(productDocInactive);
    }

    @ChangeSet(order = "003", id = "brands", author = "")
    public void insertInitialBrands(MongoTemplate mongoTemplate) {
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
        mongoTemplate.save(brandDoc);
    }

    @ChangeSet(order = "004", id = "productsWithSearchTags", author = "")
    public void insertProductsWithSearchTags(MongoTemplate mongoTemplate) {
        BrandRef biteBeauty = new BrandRef(new ObjectId(), "Bite Beauty");

        ProductDoc biteBeautyLipCrayon = new ProductDoc();
        biteBeautyLipCrayon.setBrandRef(biteBeauty);
        biteBeautyLipCrayon.setName("Power move creamy matte lip crayon");
        biteBeautyLipCrayon.setSearchTags(Arrays.asList("bite", "beauty", "power", "move", "creamy", "matte", "lip", "crayon"));
        mongoTemplate.save(biteBeautyLipCrayon);

        BrandRef lakme = new BrandRef(new ObjectId(), "Lakme");
        ProductDoc lakmefacePowder = new ProductDoc();
        lakmefacePowder.setBrandRef(lakme);
        lakmefacePowder.setName("Face powder Soft pink");
        lakmefacePowder.setSearchTags(Arrays.asList("lakme", "face", "powder", "soft", "pink"));
        mongoTemplate.save(lakmefacePowder);

        ProductDoc lakmeLiquidFoundationPearl = new ProductDoc();
        lakmeLiquidFoundationPearl.setBrandRef(lakme);
        lakmeLiquidFoundationPearl.setName("Liquid foundation Pearl");
        lakmeLiquidFoundationPearl.setSearchTags(Arrays.asList("lakme", "liquid", "foundation", "pearl"));
        mongoTemplate.save(lakmeLiquidFoundationPearl);

        ProductDoc lakmeLiquidFoundationMarble = new ProductDoc();
        lakmeLiquidFoundationMarble.setBrandRef(lakme);
        lakmeLiquidFoundationMarble.setName("Liquid foundation Marble");
        lakmeLiquidFoundationMarble.setSearchTags(Arrays.asList("lakme", "liquid", "foundation", "marble"));
        mongoTemplate.save(lakmeLiquidFoundationMarble);

        BrandRef lotus = new BrandRef(new ObjectId(), "Lotus");
        ProductDoc lotusHerbalSindoor = new ProductDoc();
        lotusHerbalSindoor.setBrandRef(lotus);
        lotusHerbalSindoor.setName("Herbals divine dew herbal sindoor");
        lotusHerbalSindoor.setSearchTags(Arrays.asList("lotus", "herbals", "divine", "dew", "herbal", "sindoor"));
        mongoTemplate.save(lotusHerbalSindoor);

        ProductDoc lakmeJewelSindoorMaroon = new ProductDoc();
        lakmeJewelSindoorMaroon.setBrandRef(lakme);
        lakmeJewelSindoorMaroon.setName("Jewel Sindoor Maroon");
        lakmeJewelSindoorMaroon.setSearchTags(Arrays.asList("lakme", "jewel", "sindoor", "maroon"));
        mongoTemplate.save(lakmeJewelSindoorMaroon);

        ProductDoc lakmeJewelSindoorRed = new ProductDoc();
        lakmeJewelSindoorRed.setBrandRef(lakme);
        lakmeJewelSindoorRed.setName("Jewel Sindoor Red");
        lakmeJewelSindoorRed.setSearchTags(Arrays.asList("lakme", "jewel", "sindoor", "red"));
        mongoTemplate.save(lakmeJewelSindoorRed);

        BrandRef loreal = new BrandRef(new ObjectId(), "Loreal");
        ProductDoc lorealFoundation = new ProductDoc();
        lorealFoundation.setBrandRef(loreal);
        lorealFoundation.setName("Paris Infallible 24H fresh wear foundation");
        lorealFoundation.setSearchTags(Arrays.asList("loreal", "paris", "infallible", "24h", "fresh", "wear", "foundation"));
        mongoTemplate.save(lorealFoundation);
    }
}
