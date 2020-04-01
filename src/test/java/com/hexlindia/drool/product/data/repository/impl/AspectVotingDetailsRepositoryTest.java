package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.common.data.doc.ProductRef;
import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.product.data.doc.AspectVotingDetailsDoc;
import com.hexlindia.drool.product.data.doc.AspectVotingDoc;
import com.hexlindia.drool.product.data.repository.api.AspectVotingDetailsRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AspectVotingDetailsRepositoryTest {

    @Autowired
    AspectVotingDetailsRepository aspectVotingDetailsRepository;

    @Test
    void save() {
        AspectVotingDoc aspectVotingDocStyle = new AspectVotingDoc();
        aspectVotingDocStyle.setAspectId("1");
        aspectVotingDocStyle.setSelectedOptions(Arrays.asList("Retro", "Chic"));
        AspectVotingDoc aspectVotingDocOccasion = new AspectVotingDoc();
        aspectVotingDocOccasion.setAspectId("2");
        aspectVotingDocOccasion.setSelectedOptions(Arrays.asList("Wedding", "Party"));
        AspectVotingDetailsDoc aspectVotingDetailsDoc = new AspectVotingDetailsDoc(null, new ObjectId(), Arrays.asList(aspectVotingDocStyle, aspectVotingDocOccasion), new ProductRef("123", "Collosal Kajal", "Kajal"), new UserRef("u123", "username123"));
        assertNotNull(aspectVotingDetailsRepository.save(aspectVotingDetailsDoc));
    }
}