package com.example.blogapi.article;

import com.example.blogapi.comment.CommentDTO;
import com.example.blogapi.comment.CommentEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description="Article DTO Information"
)
public class ArticleDTO {

    private Long id;
    @Schema(
            description="Article Title"
    )
    @NotEmpty
    @Size(min=2,message="The title should not be less than 2 characters")
    private String title;
    @Schema(
            description="Article Description"
    )
    @NotEmpty
    @Size(min=10,message="Description should not have less than 10 characters.")
    private String description;
    @Schema(
            description="Article Content"
    )
    @NotEmpty
    private String content;
    @Schema(
            description="Comments on the Article"
    )
    private Set<CommentDTO> comments;
    @Schema(
            description="Category of the Article"
    )
    private long categoryId;
}
