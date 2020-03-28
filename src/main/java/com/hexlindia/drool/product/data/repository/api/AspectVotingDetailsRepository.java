package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.data.doc.AspectVotingDetailsDoc;
import org.bson.types.ObjectId;

public interface AspectVotingDetailsRepository {


    ObjectId save(AspectVotingDetailsDoc aspectVotingDetailsDoc);
}
