package com.example.blogapi.article;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ArticleResponse {
    List<ArticleDTO> articles;
    int pageNo;
    int pageSize;
    long totalElements;
    int totalPages;
    boolean last;
}
