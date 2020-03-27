package com.hexlindia.drool.product.data.doc;

import com.hexlindia.drool.product.business.impl.usecase.ReviewType;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDoc {

    private ObjectId id;
    private ReviewType reviewType;
    private String recommendation;
    private String detailedReview;
    private String reviewSummary;
    private ObjectId videoId;
    private LocalDateTime datePosted;


    public ReviewDoc() {
        this.id = ObjectId.get();
    }
}
