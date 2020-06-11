package com.hexlindia.drool.article.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleMinimalPreview {

    private int id;
    private String title;
    private String coverPicture;
}
