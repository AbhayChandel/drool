package com.hexlindia.drool.common.data.doc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRef {

    private ObjectId id;
    private String reply;
    private PostRef postRef;
    private LocalDateTime datePosted;
}
