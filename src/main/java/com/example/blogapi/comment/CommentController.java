package com.example.blogapi.comment;

import com.example.blogapi.Exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    CommentService commentService;
    CommentController(CommentService cs){
        this.commentService=cs;

    }
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentDTO> createComments(@PathVariable long articleId, @Valid @RequestBody CommentDTO comment) throws ResourceNotFoundException {
        return new ResponseEntity<>(commentService.createComment(articleId,comment), HttpStatus.CREATED);
    }
    @GetMapping("/articles/{articleId}/comments")
    public List<CommentDTO> getAllComments(@PathVariable long articleId){
        return commentService.getCommentsByArticleId(articleId);
    }

    @GetMapping("/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long articleId,@PathVariable long commentId) throws ResourceNotFoundException {
        CommentDTO comment=commentService.getCommentByCommentId(articleId,commentId);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable long articleId,@PathVariable long commentId,@Valid @RequestBody CommentDTO comment) throws ResourceNotFoundException {
        CommentDTO c=commentService.updateComment(articleId,commentId,comment);
        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public String deleteComment(@PathVariable long articleId,@PathVariable long commentId) throws ResourceNotFoundException {
        return commentService.deleteComment(articleId,commentId);
    }
}
