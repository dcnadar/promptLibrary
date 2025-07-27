package com.assignment.promptlibrary.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mongodb.DuplicateKeyException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserException.ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleUserNotFound(UserException.ResourceNotFoundException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 404);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(404).body(error);
  }

  @ExceptionHandler(UserException.ConflictException.class)
  public ResponseEntity<Map<String, Object>> handleConflict(UserException.ConflictException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 409);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(409).body(error);
  }

  @ExceptionHandler(UserException.UnauthorizedException.class)
  public ResponseEntity<Map<String, Object>> handleUnauthorized(UserException.UnauthorizedException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 403);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(403).body(error);
  }

  @ExceptionHandler(UserException.BadRequestException.class)
  public ResponseEntity<Map<String, Object>> handleUserBadRequest(UserException.BadRequestException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 400);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(400).body(error);
  }

  // prompt

  @ExceptionHandler(PromptException.ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handlePromptNotFound(PromptException.ResourceNotFoundException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 404);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(404).body(error);
  }

  @ExceptionHandler(PromptException.UnauthorizedException.class)
  public ResponseEntity<Map<String, Object>> handlePromptUnauthorized(PromptException.UnauthorizedException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 403);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(403).body(error);
  }

  @ExceptionHandler(PromptException.BadRequestException.class)
  public ResponseEntity<Map<String, Object>> handlePromptBadRequest(PromptException.BadRequestException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 400);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(400).body(error);
  }

  // comment
  @ExceptionHandler(CommentException.ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleCommentNotFound(CommentException.ResourceNotFoundException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 404);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(404).body(error);
  }

  @ExceptionHandler(CommentException.UnauthorizedException.class)
  public ResponseEntity<Map<String, Object>> handleCommentUnauthorized(CommentException.UnauthorizedException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 403);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(403).body(error);
  }

  @ExceptionHandler(CommentException.BadRequestException.class)
  public ResponseEntity<Map<String, Object>> handleCommentBadRequest(CommentException.BadRequestException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 400);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(400).body(error);
  }

  // like

  @ExceptionHandler(LikeException.ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleLikePromptNotFound(LikeException.ResourceNotFoundException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 404);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(404).body(error);
  }

  @ExceptionHandler(LikeException.ConflictException.class)
  public ResponseEntity<Map<String, Object>> handleAlreadyLiked(LikeException.ConflictException ex) {
    ex.printStackTrace();
    Map<String, Object> error = new HashMap<>();
    error.put("status", 409);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(409).body(error);
  }

  @ExceptionHandler(LikeException.BadRequestException.class)
  public ResponseEntity<Map<String, Object>> handleNotLiked(LikeException.BadRequestException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 400);
    error.put("message", ex.getMessage());
    return ResponseEntity.status(400).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 500);
    error.put("message", "Internal Server Error: " + ex.getMessage());
    return ResponseEntity.status(500).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
    String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    Map<String, Object> error = new HashMap<>();
    error.put("status", 400);
    error.put("message", errorMessage);
    return ResponseEntity.status(400).body(error);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<Map<String, Object>> handleDuplicateKey(DuplicateKeyException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 409);
    error.put("message", "Duplicate key error: Email or Username already exists");
    return ResponseEntity.status(409).body(error);
  }

}
