package com.example.blogapi.article;

import com.example.blogapi.Category.CategoryEntity;
import com.example.blogapi.Category.CategoryRepository;
import com.example.blogapi.Exceptions.BlogApiException;
import com.example.blogapi.Exceptions.ResourceNotFoundException;
import com.example.blogapi.user.UserEntity;
import com.example.blogapi.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    ArticleRepository articleRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;
    ModelMapper mapper;
    public ArticleService(
            ArticleRepository ar,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            ModelMapper mapper){

        this.articleRepository=ar;
        this.categoryRepository=categoryRepository;
        this.mapper=mapper;
        this.userRepository=userRepository;
    }

    ArticleEntity mapDTOToArticle(ArticleDTO articleDTO){
        return mapper.map(articleDTO,ArticleEntity.class);
    }
    ArticleDTO mapArticletoArticleDTO(ArticleEntity article){
        return mapper.map(article,ArticleDTO.class);
    }

    public ArticleDTO createArticle(ArticleDTO article) throws ResourceNotFoundException {
        CategoryEntity category=categoryRepository.findByName(article.getCategory()).orElseThrow(
                ()->new ResourceNotFoundException("Category","Name",article.getCategory().toLowerCase())
        );
        ArticleEntity articleToSave=mapDTOToArticle(article);
        articleToSave.setCategory(category);
        String author=SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user=userRepository.findByUsernameOrEmail(author,author).orElseThrow(
                ()->new BlogApiException(HttpStatus.BAD_REQUEST,"Author not found")
        );
        articleToSave.setAuthor(user);
        return mapArticletoArticleDTO(articleRepository.save(articleToSave));
    }


    public ArticleResponse getAllArticles(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable page= PageRequest.of(pageNo,pageSize,sort);
        Page<ArticleEntity> content=articleRepository.findAll(page);
        List<ArticleEntity> articles=content.getContent();
        List<ArticleDTO>articlesList=articles.stream().map((article)->mapArticletoArticleDTO(article)).collect(Collectors.toList());
        return new ArticleResponse(articlesList,page.getPageNumber(),page.getPageSize(),content.getTotalElements(), content.getTotalPages(), content.isLast());
    }

    public ArticleDTO getArticle(long id) throws ResourceNotFoundException {
        ArticleEntity article=articleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Article","ID",id));
        return mapArticletoArticleDTO(article);
    }

    public ArticleDTO updateArticle(ArticleDTO article,long id) throws ResourceNotFoundException {
        ArticleEntity result=articleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Article","ID",id));
        CategoryEntity category=categoryRepository.findByName(article.getCategory().toLowerCase()).orElseThrow(
                ()->new ResourceNotFoundException("Category","Name",article.getCategory().toLowerCase())
        );
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        if(username.equalsIgnoreCase(result.getAuthor().getUsername())) {
            result.setTitle(article.getTitle());
            result.setDescription(article.getDescription());
            result.setContent(article.getContent());
            result.setCategory(category);
            ArticleEntity updatedArticle = articleRepository.save(result);
            return mapArticletoArticleDTO(updatedArticle);
        }
        else{
            throw new BlogApiException(HttpStatus.UNAUTHORIZED,"User can edit only their own articles.");
        }
    }

    public void deleteArticle(long id) throws ResourceNotFoundException {
        ArticleEntity result=articleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Article","ID",id));
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        if(username.equalsIgnoreCase(result.getAuthor().getUsername()) || username.equalsIgnoreCase(result.getAuthor().getEmail())) {
            articleRepository.delete(result);
        }
        else{
            throw new BlogApiException(HttpStatus.UNAUTHORIZED,"User can delete only their own articles");
        }
    }

    public List<ArticleDTO>getArticleByCategory(long categoryId) throws ResourceNotFoundException {
        CategoryEntity category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","ID",categoryId));
        List<ArticleEntity>articles=articleRepository.findByCategoryId(categoryId);
        return articles.stream().map((article)->mapArticletoArticleDTO(article)).collect(Collectors.toList());
    }
}
