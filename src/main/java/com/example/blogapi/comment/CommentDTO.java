package com.example.blogapi.comment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    long id;
    @NotEmpty
    String name;
    @NotEmpty
    @Email
    String email;
    @NotEmpty
    String body;
}
