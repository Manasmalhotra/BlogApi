package com.example.blogapi.article;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleDTO {
    private Long id;
    private String title;
    private String description;
    private String content;
}
