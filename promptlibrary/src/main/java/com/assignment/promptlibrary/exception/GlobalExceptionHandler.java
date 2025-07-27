package com.assignment.promptlibrary.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.assignment.promptlibrary.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
    ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
    return ResponseEntity.status(status).body(errorResponse);
  }

  // user
  @ExceptionHandler(UserException.ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(UserException.ResourceNotFoundException ex) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(UserException.ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflict(UserException.ConflictException ex) {
    return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(UserException.UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorized(UserException.UnauthorizedException ex) {
    return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
  }

  @ExceptionHandler(UserException.BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleUserBadRequest(UserException.BadRequestException ex) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(UserException.DatabaseException.class)
  public ResponseEntity<ErrorResponse> handleUserDatabaseException(UserException.DatabaseException ex) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }
  // prompt

  @ExceptionHandler(PromptException.ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePromptNotFound(PromptException.ResourceNotFoundException ex) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(PromptException.UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handlePromptUnauthorized(PromptException.UnauthorizedException ex) {
    return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
  }

  @ExceptionHandler(PromptException.BadRequestException.class)
  public ResponseEntity<ErrorResponse> handlePromptBadRequest(PromptException.BadRequestException ex) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  // comment
  @ExceptionHandler(CommentException.ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCommentNotFound(CommentException.ResourceNotFoundException ex) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(CommentException.UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleCommentUnauthorized(CommentException.UnauthorizedException ex) {
    return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
  }

  @ExceptionHandler(CommentException.BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleCommentBadRequest(CommentException.BadRequestException ex) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  // like

  @ExceptionHandler(LikeException.ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleLikePromptNotFound(LikeException.ResourceNotFoundException ex) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(LikeException.ConflictException.class)
  public ResponseEntity<ErrorResponse> handleAlreadyLiked(LikeException.ConflictException ex) {
    return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(LikeException.BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleNotLiked(LikeException.BadRequestException ex) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  // Global

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
    String message = "Internal Server Error: " + ex.getMessage();
    return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ErrorResponse> handleDatabaseError(DataAccessException ex) {
    String message = "Database Error: " + ex.getMessage();
    return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }
}
