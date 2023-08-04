package com.example.blogapi.article;

import com.example.blogapi.Exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    ArticleRepository articleRepository;
    ModelMapper mapper;
    public ArticleService(ArticleRepository ar,ModelMapper mapper){

        this.articleRepository=ar;
        this.mapper=mapper;
    }

    ArticleEntity mapDTOToArticle(ArticleDTO articleDTO){
        return mapper.map(articleDTO,ArticleEntity.class);
    }
    ArticleDTO mapArticletoArticleDTO(ArticleEntity article){
        return mapper.map(article,ArticleDTO.class);
    }

    public ArticleDTO createArticle(ArticleDTO article) {
        ArticleEntity articleToSave=mapDTOToArticle(article);
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
        result.setTitle(article.getTitle());
        result.setDescription(article.getDescription());
        result.setContent(article.getContent());
        ArticleEntity updatedArticle=articleRepository.save(result);
        return mapArticletoArticleDTO(updatedArticle);
    }

    public void deleteArticle(long id) throws ResourceNotFoundException {
        ArticleEntity result=articleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Article","ID",id));
        articleRepository.delete(result);
    }
}
