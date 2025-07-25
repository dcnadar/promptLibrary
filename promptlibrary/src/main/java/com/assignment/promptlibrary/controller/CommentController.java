package com.assignment.promptlibrary.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.assignment.promptlibrary.dto.CommentDTO;

import com.assignment.promptlibrary.response.CommentApiResponse;
import com.assignment.promptlibrary.service.CommentService;

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

  @PostMapping("/prompts/{promptId}/comments")
  public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO, @PathVariable String promptId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    boolean res = commentService.addComment(commentDTO, promptId, username);
    if (res) {
      return ResponseEntity.ok(new CommentApiResponse(200, "Comment Added", null));
    }
    return ResponseEntity.status(400).body(new CommentApiResponse(400, "Failed to Add Comment", null));
  }

  @DeleteMapping("/prompts/{promptId}/comments/{commentId}")
  public ResponseEntity<?> deleteComment(@PathVariable String promptId, @PathVariable String commentId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    boolean res = commentService.deleteComment(promptId, commentId, username);
    if (res) {
      return ResponseEntity.ok(new CommentApiResponse(200, "Comment Deleted", null));
    }
    return ResponseEntity.status(403).body(new CommentApiResponse(403, "Unauthorized or Not Found", null));
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
