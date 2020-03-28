package com.hexlindia.drool.product.data.doc;

import com.hexlindia.drool.common.data.doc.ProductRef;
import com.hexlindia.drool.common.data.doc.UserRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "aspect_voting_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AspectVotingDetailsDoc {

    private ObjectId id;
    private List<AspectVotingDoc> aspectVotingDocList;
    private ProductRef productRef;
    private UserRef userRef;
}
