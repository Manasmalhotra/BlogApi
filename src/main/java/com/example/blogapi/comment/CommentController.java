package com.example.blogapi.comment;

import com.example.blogapi.Exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name="CRUD REST APIs for Comment Resource")
public class CommentController {
    CommentService commentService;
    CommentController(CommentService cs){
        this.commentService=cs;

    }
    @Operation(
            summary="Create comments REST API",
            description="Create comments REST API is used to save comments in the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTT Status 201 Created"
    )
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentDTO> createComments(@PathVariable long articleId, @Valid @RequestBody CommentDTO comment) throws ResourceNotFoundException {
        return new ResponseEntity<>(commentService.createComment(articleId,comment), HttpStatus.CREATED);
    }

    @Operation(
            summary="Get All Comments REST API",
            description="Get All Comments REST API is used to fetch all the comments on a given article from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )

    @GetMapping("/articles/{articleId}/comments")
    public List<CommentDTO> getAllComments(@PathVariable long articleId){
        return commentService.getCommentsByArticleId(articleId);
    }

    @Operation(
            summary="Get Comment With Comment ID REST API",
            description="Get Comment With Comment ID REST API is used to fetch a particular comment on a given article from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @GetMapping("/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long articleId,@PathVariable long commentId) throws ResourceNotFoundException {
        CommentDTO comment=commentService.getCommentByCommentId(articleId,commentId);
        return ResponseEntity.ok(comment);
    }

    @Operation(
            summary="Update Comment REST API",
            description="Update Comment REST API is used to edit the comment  with the given id and save the updated comment in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @PutMapping("/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable long articleId,@PathVariable long commentId,@Valid @RequestBody CommentDTO comment) throws ResourceNotFoundException {
        CommentDTO c=commentService.updateComment(articleId,commentId,comment);
        return ResponseEntity.ok(c);
    }

    @Operation(
            summary="Delete Comment REST API",
            description="Delete Comment REST API is used to delete the comment with the given id from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public String deleteComment(@PathVariable long articleId,@PathVariable long commentId) throws ResourceNotFoundException {
        return commentService.deleteComment(articleId,commentId);
    }
}
