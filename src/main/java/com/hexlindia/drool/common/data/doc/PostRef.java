package com.hexlindia.drool.common.data.doc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRef {

    private ObjectId id;
    private String title;
    private String type;
    private String medium;
    private LocalDateTime datePosted;
}
