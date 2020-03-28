package com.hexlindia.drool.product.data.doc;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AspectVotingDoc {

    private String aspectId;
    private List<String> selectedOptions;

}
