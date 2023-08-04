package com.example.blogapi.comment;

import com.example.blogapi.Exceptions.BlogApiException;
import com.example.blogapi.Exceptions.ResourceNotFoundException;
import com.example.blogapi.article.ArticleEntity;
import com.example.blogapi.article.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    CommentRepository commentRepository;
    ArticleRepository articleRepository;
    ModelMapper mapper;
    CommentService(CommentRepository cr,ArticleRepository ar,ModelMapper mapper){
        this.commentRepository=cr;
        this.articleRepository=ar;
        this.mapper=mapper;
    }


    private CommentDTO mapToDTO(CommentEntity comment){
        return mapper.map(comment,CommentDTO.class);
    }

    private CommentEntity mapToEntity(CommentDTO commentDTO){
        return mapper.map(commentDTO,CommentEntity.class);
    }

    private CommentEntity checks(long articleId, long commentId) throws ResourceNotFoundException {
        CommentEntity comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","ID",commentId)
        );
        ArticleEntity article=articleRepository.findById(articleId).orElseThrow(
                ()->new ResourceNotFoundException("Article","ID",articleId)
        );
        if(comment.getArticle().getId()!=article.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to the post");
        }
        return comment;
    }

    CommentDTO createComment(long articleId, CommentDTO commentDTO) throws ResourceNotFoundException {
        ArticleEntity article=articleRepository.findById(articleId).orElseThrow(
                ()->new ResourceNotFoundException("Article","ID",articleId)
        );
        CommentEntity comment=mapToEntity(commentDTO);
        comment.setArticle(article);
        CommentEntity newComment=commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    List<CommentDTO> getCommentsByArticleId(long articleId){
        List<CommentEntity>comments=commentRepository.findByArticleId(articleId);
        List<CommentDTO> commentDTOList=comments.stream().map((comment)->mapToDTO(comment)).collect(Collectors.toList());
        return commentDTOList;
    }

    CommentDTO getCommentByCommentId(long articleId,long commentId) throws ResourceNotFoundException {
        CommentEntity comment=checks(articleId, commentId);
        return mapToDTO(comment);
    }

    public CommentDTO updateComment(long articleId, long commentId,CommentDTO commentRequest) throws ResourceNotFoundException {
        ArticleEntity article=articleRepository.findById(articleId).orElseThrow(
                ()->new ResourceNotFoundException("Article","ID",articleId)
        );
        CommentEntity comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","ID",commentId)
        );
        if(comment.getArticle().getId()!=article.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to the post");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        CommentEntity commentResponse=commentRepository.save(comment);
        return mapToDTO(commentResponse);
    }

    public String deleteComment(long articleId, long commentId) throws ResourceNotFoundException {
        CommentEntity comment=checks(articleId, commentId);
        commentRepository.delete(comment);
        return "Deleted the Comment Successfully.";
    }


}
