package com.example.blogapi.Category;

import com.example.blogapi.article.ArticleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories")
public class CategoryEntity {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;
    @OneToMany(mappedBy = "category",cascade=CascadeType.ALL,orphanRemoval = true)
    List<ArticleEntity> articles;

}
