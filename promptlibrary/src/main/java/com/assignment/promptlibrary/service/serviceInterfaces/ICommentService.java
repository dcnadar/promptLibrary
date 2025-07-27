package com.assignment.promptlibrary.service.serviceInterfaces;

import java.util.List;

import com.assignment.promptlibrary.dto.CommentDTO;

public interface ICommentService {
  void addComment(CommentDTO commentDTO, String promptId, String username);

  void deleteComment(String promptId, String commentId, String username);

  List<CommentDTO> getAllComments(String promptId);
}
