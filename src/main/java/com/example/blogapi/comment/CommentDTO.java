package com.example.blogapi.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description="Comment DTO Model Information")
public class CommentDTO {
    long id;
    @Schema(description="Name of the person adding the comment")
    @NotEmpty
    String name;
    @Schema(description="Email of the person adding the comment")
    @NotEmpty
    @Email
    String email;
    @Schema(description="Content of teh comment")
    @NotEmpty
    String body;
}
