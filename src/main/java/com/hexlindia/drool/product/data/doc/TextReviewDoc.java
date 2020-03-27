package com.hexlindia.drool.product.data.doc;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter
public class TextReviewDoc {

    private ObjectId id;
    private String reviewType;
    private String recommendation;
    private String detailedReview;
    private String reviewSummary;
    private LocalDateTime datePosted;


    public TextReviewDoc() {
        this.id = ObjectId.get();
    }
}
