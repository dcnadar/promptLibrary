package com.assignment.promptlibrary.response;

import java.util.List;

import com.assignment.promptlibrary.dto.CommentDTO;

public class CommentApiResponse {

  private int statusCode;
  private String message;
  private CommentDTO commentDTO;
  private List<CommentDTO> commentDTOlist;

  public CommentApiResponse() {
  }

  public CommentApiResponse(int statusCode, String message, CommentDTO commentDTO) {
    this.statusCode = statusCode;
    this.message = message;
    this.commentDTO = commentDTO;
  }

  public CommentApiResponse(int statusCode, List<CommentDTO> commentDTOlist) {
    this.statusCode = statusCode;

    this.commentDTOlist = commentDTOlist;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public CommentDTO getCommentDTO() {
    return commentDTO;
  }

  public void setCommentDTO(CommentDTO commentDTO) {
    this.commentDTO = commentDTO;
  }

  public List<CommentDTO> getCommentDTOlist() {
    return commentDTOlist;
  }

  public void setCommentDTOlist(List<CommentDTO> commentDTOlist) {
    this.commentDTOlist = commentDTOlist;
  }

  @Override
  public String toString() {
    return "CommentApiResponse [statusCode=" + statusCode + ", message=" + message + ", commentDTO=" + commentDTO
        + ", commentDTOlist=" + commentDTOlist + "]";
  }

}
