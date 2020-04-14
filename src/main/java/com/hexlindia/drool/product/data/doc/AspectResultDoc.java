package com.hexlindia.drool.product.data.doc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AspectResultDoc {
    private String id;
    private String displayComponent;
    private String title;
    private Integer votes;
    private List<AspectOption> options;

    public AspectResultDoc(String id, String displayComponent, String title, Integer votes, List<AspectOption> options) {
        this.id = id;
        this.displayComponent = displayComponent;
        this.title = title;
        this.votes = votes;
        this.options = new ArrayList<>();
        this.options = options;
    }
}
