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
public class ProductCollectionChangeLog {

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
}
