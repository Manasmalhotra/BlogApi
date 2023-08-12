package com.example.blogapi.article;

import com.example.blogapi.Exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.blogapi.utils.constants.*;


@RestController
@RequestMapping("/api/articles")
@Tag(name="CRUD REST APIs for Article Resource")
public class ArticleController {
    ArticleService articleService;
    @Autowired
    public ArticleController(ArticleService as){
        this.articleService=as;
    }

    @Operation(
            summary="Create Articles REST API",
            description="Create articles REST API is used to save articles in the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTT Status 201 Created"
    )
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addarticle")
    ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody ArticleDTO article) throws ResourceNotFoundException {

        ArticleDTO articleResponse=articleService.createArticle(article);
        return new ResponseEntity<>(articleResponse,HttpStatus.CREATED);
    }

    @Operation(
            summary="Get All Articles REST API",
            description="Get all  articles REST API is used to fetch all the articles from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @GetMapping
    ArticleResponse getAllArticles(
            @RequestParam(value="pageNo",defaultValue=DEFAULT_PAGENO,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue =DEFAULT_PAGESIZE,required=false) int pageSize,
            @RequestParam(value="sortBy",defaultValue=DEFAULT_SORTBY,required=false) String sortBy,
            @RequestParam(value="sortDir",defaultValue=DEFAULT_SORTBYDIRECTION,required=false) String sortDir){

        return articleService.getAllArticles(pageNo,pageSize,sortBy,sortDir);

    }

    @Operation(
            summary="Get Article By Id REST API",
            description="Get Article By ID REST API is used to fetch the article with the given id from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @GetMapping("/{id}")
    ResponseEntity<ArticleDTO> getArticle(@PathVariable long id){
        ArticleDTO article;

        try {
            article = articleService.getArticle(id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(article);
    }

    @Operation(
            summary="Update Article REST API",
            description="Update Article REST API is used to edit the article with the given id and save the updated article in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @PutMapping("/{id}")
    ResponseEntity<ArticleDTO> updateArticle(@Valid @RequestBody ArticleDTO article,@PathVariable long id){

        ArticleDTO result;
        try {
            result = articleService.updateArticle(article,id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary="Delete Article REST API",
            description="Delete Article REST API is used to delete the article with the given id from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteArticle(@PathVariable long id) throws ResourceNotFoundException {
        articleService.deleteArticle(id);
        return new ResponseEntity<>("Article deleted successfully",HttpStatus.OK);
    }
    @Operation(
            summary="Get All Articles Of The Given Category REST API",
            description="Get All Articles Of The Given Category REST API is used to fetch all the articles in the given category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ArticleDTO>>getArticlesByCategory(@PathVariable(value="id") long categoryId) throws ResourceNotFoundException {
        List<ArticleDTO> articles=articleService.getArticleByCategory(categoryId);
        return ResponseEntity.ok(articles);
    }
}
