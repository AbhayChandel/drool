package com.hexlindia.drool.common.data.doc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PostRef {

    private String id;
    private String title;
    private String type;
    private String medium;
    private LocalDateTime datePosted;

    public PostRef(String id, String title, String type, String medium, LocalDateTime datePosted) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.medium = medium;
        this.datePosted = datePosted;
    }
}
