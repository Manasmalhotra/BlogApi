package com.example.blogapi.article;
import com.example.blogapi.Category.CategoryEntity;
import com.example.blogapi.comment.CommentEntity;
import com.example.blogapi.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 150)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String content;
    @ManyToOne
    private UserEntity author;
    @OneToMany(mappedBy = "article",cascade=CascadeType.ALL,orphanRemoval = true)
    Set<CommentEntity> comments=new HashSet<>();
    @ManyToOne
            @JoinColumn(name="category_id")
    CategoryEntity category;
}
