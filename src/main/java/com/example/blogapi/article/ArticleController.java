package com.example.blogapi.article;

import com.example.blogapi.Exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.blogapi.utils.constants.*;


@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    ArticleService articleService;
    @Autowired
    public ArticleController(ArticleService as){
        this.articleService=as;
    }


    @PostMapping("/addarticle")
    ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody ArticleDTO article){

        ArticleDTO articleResponse=articleService.createArticle(article);
        return new ResponseEntity<>(articleResponse,HttpStatus.CREATED);
    }

    @GetMapping
    ArticleResponse getAllArticles(
            @RequestParam(value="pageNo",defaultValue=DEFAULT_PAGENO,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue =DEFAULT_PAGESIZE,required=false) int pageSize,
            @RequestParam(value="sortBy",defaultValue=DEFAULT_SORTBY,required=false) String sortBy,
            @RequestParam(value="sortDir",defaultValue=DEFAULT_SORTBYDIRECTION,required=false) String sortDir){

        return articleService.getAllArticles(pageNo,pageSize,sortBy,sortDir);

    }

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

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteArticle(@PathVariable long id) throws ResourceNotFoundException {
        articleService.deleteArticle(id);
        return new ResponseEntity<>("Article deleted successfully",HttpStatus.OK);
    }
}
