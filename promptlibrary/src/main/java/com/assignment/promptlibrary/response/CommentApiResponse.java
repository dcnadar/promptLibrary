package com.assignment.promptlibrary.response;

import java.util.List;

import com.assignment.promptlibrary.dto.CommentDTO;

public class CommentApiResponse {

  private String message;
  private CommentDTO commentDTO;
  private List<CommentDTO> commentDTOlist;

  public CommentApiResponse() {
  }

  public CommentApiResponse(String message) {
    this.message = message;
  }

  public CommentApiResponse(String message, CommentDTO commentDTO) {

    this.message = message;
    this.commentDTO = commentDTO;
  }

  public CommentApiResponse(String message, List<CommentDTO> commentDTOlist) {
    this.message = message;
    this.commentDTOlist = commentDTOlist;
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
    return "CommentApiResponse [ message=" + message + ", commentDTO=" + commentDTO
        + ", commentDTOlist=" + commentDTOlist + "]";
  }

}
