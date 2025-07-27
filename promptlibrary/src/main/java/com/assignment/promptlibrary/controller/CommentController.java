package com.assignment.promptlibrary.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.assignment.promptlibrary.dto.CommentDTO;

import com.assignment.promptlibrary.response.CommentApiResponse;
import com.assignment.promptlibrary.service.CommentService;
import com.assignment.promptlibrary.utils.AuthUtils;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PreAuthorize("hasAuthority('BUYER')")
  @PostMapping("/prompts/{promptId}/comments")
  public ResponseEntity<?> addComment(@Valid @RequestBody CommentDTO commentDTO, @PathVariable String promptId) {

    String username = AuthUtils.getCurrentUsername();
    commentService.addComment(commentDTO, promptId, username);
    return ResponseEntity.ok(new CommentApiResponse(200, "Comment Added", null));
  }

  @PreAuthorize("hasAuthority('BUYER') or hasAuthority('SELLER')")
  @DeleteMapping("/prompts/{promptId}/comments/{commentId}")
  public ResponseEntity<?> deleteComment(@PathVariable String promptId, @PathVariable String commentId) {

    String username = AuthUtils.getCurrentUsername();
    commentService.deleteComment(promptId, commentId, username);
    return ResponseEntity.ok(new CommentApiResponse(200, "Comment Deleted", null));
  }

  @GetMapping("/prompts/{promptId}/comments")
  public ResponseEntity<?> getAllComments(@PathVariable String promptId) {
    List<CommentDTO> dtoList = commentService.getAllComments(promptId);
    if (dtoList != null) {
      return ResponseEntity.ok(new CommentApiResponse(200, dtoList));
    }
    return ResponseEntity.ok(new CommentApiResponse(409, null));
  }
}
