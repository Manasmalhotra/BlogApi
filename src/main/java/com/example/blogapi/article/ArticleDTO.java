package com.example.blogapi.article;

import com.example.blogapi.comment.CommentEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private Long id;
    @NotEmpty
    @Size(min=2,message="The title should not be less than 2 characters")
    private String title;
    @NotEmpty
    @Size(min=10,message="Description should not have less than 10 characters.")
    private String description;
    @NotEmpty
    private String content;
    Set<CommentEntity> comments;
}
