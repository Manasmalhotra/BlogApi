package com.example.blogapi.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="Category DTO Model Information")
public class CategoryDTO {
    @Schema(description="ID of the category")
    long id;
    @Schema(description="Name of the category")
    String name;
    @Schema(description="Description of the category")
    String description;
}
