package com.assignment.promptlibrary.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.CommentDao;
import com.assignment.promptlibrary.dto.CommentDTO;
import com.assignment.promptlibrary.model.Comment;

@Service
public class CommentService {

  private final CommentDao commentDao;

  public CommentService(CommentDao commentDao) {
    this.commentDao = commentDao;
  }

  public boolean addComment(CommentDTO commentDTO, String promptId) {
    Comment comment = new Comment();
    BeanUtils.copyProperties(commentDTO, comment);
    String result = commentDao.addComment(comment, promptId);
    if ("saved".equalsIgnoreCase(result)) {
      return true;
    }
    return false;
  }

  public List<CommentDTO> getAllComments(String promptId) {
    List<Comment> commentList = commentDao.getAllComments(promptId);
    List<CommentDTO> dtoList = commentList.stream().map(comment -> {
      CommentDTO commentDTO = new CommentDTO();
      BeanUtils.copyProperties(comment, commentDTO);
      return commentDTO;
    }).collect(Collectors.toList());
    return dtoList;
  }

  public boolean deleteComment(String promptId, String commentId) {
    boolean res = commentDao.deleteComment(promptId, commentId);
    return false;
  }

}
