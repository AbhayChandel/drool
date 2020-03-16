package com.hexlindia.drool.common.datamigration.test.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.hexlindia.drool.product.data.doc.AspectDoc;
import com.hexlindia.drool.product.data.doc.AspectOption;
import com.hexlindia.drool.product.data.doc.ProductDoc;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@ChangeLog
public class ProductCollectionChangeLog {

    @ChangeSet(order = "002", id = "product", author = "")
    public void insertInitialProduct(MongoTemplate mongoTemplate) {
        AspectDoc aspectStyle = new AspectDoc("1", "pc", "Top Styles", 45,
                Arrays.asList(new AspectOption("Retro", 25), new AspectOption("Chic", 45),
                        new AspectOption("Bohemian", 5)));
        AspectDoc aspectOccasion = new AspectDoc("2", "pc", "Occasion", 35,
                Arrays.asList(new AspectOption("Wedding", 5), new AspectOption("Clubbing", 35)));
        ProductDoc productDocActive = new ProductDoc("Lakme 9 to 5", Arrays.asList(aspectStyle, aspectOccasion));
        productDocActive.setActive(true);
        mongoTemplate.insert(productDocActive);
    }
}
