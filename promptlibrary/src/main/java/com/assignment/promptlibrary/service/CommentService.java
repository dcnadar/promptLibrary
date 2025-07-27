package com.assignment.promptlibrary.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.CommentDao;
import com.assignment.promptlibrary.dao.PromptDao;
import com.assignment.promptlibrary.dao.UserDao;
import com.assignment.promptlibrary.dto.CommentDTO;
import com.assignment.promptlibrary.exception.CommentException;
import com.assignment.promptlibrary.model.Comment;
import com.assignment.promptlibrary.model.Prompt;
import com.assignment.promptlibrary.model.User;
import com.assignment.promptlibrary.service.serviceInterfaces.ICommentService;

@Service
public class CommentService implements ICommentService {

  private final CommentDao commentDao;

  private final UserDao userDao;
  private final PromptDao promptDao;

  public CommentService(CommentDao commentDao, UserDao userDao, PromptDao promptDao) {
    this.commentDao = commentDao;
    this.userDao = userDao;
    this.promptDao = promptDao;
  }

  @Override
  public void addComment(CommentDTO commentDTO, String promptId, String username) {
    Prompt prompt = promptDao.getPrompt(promptId);
    if (prompt == null) {
      throw new CommentException.ResourceNotFoundException("Prompt not found");
    }
    User user = userDao.findUserByUsername(username);
    Comment comment = new Comment();
    BeanUtils.copyProperties(commentDTO, comment);
    comment.setPromptId(promptId);
    comment.setUserId(user.getId());
    boolean res = commentDao.addComment(comment);
    if (res) {
      return;
    }
    throw new CommentException.BadRequestException("Can not create comment currently");
  }

  @Override
  public void deleteComment(String promptId, String commentId, String username) {
    User user = userDao.findUserByUsername(username);
    Comment comment = commentDao.findCommentById(commentId);
    if (comment == null || !comment.getPromptId().equals(promptId)) {
      throw new CommentException.ResourceNotFoundException("Comment not found");
    }

    Prompt prompt = promptDao.getPrompt(promptId);
    if (prompt == null) {
      throw new CommentException.ResourceNotFoundException("Prompt not found");
    }
    boolean isOwner = prompt.getCreatedBy().equals(user.getId());
    boolean isAuthor = comment.getUserId().equals(user.getId());
    if (!isOwner && !isAuthor) {
      throw new CommentException.UnauthorizedException("Not authorized to delete this comment");
    }
    commentDao.deleteCommentById(commentId);
  }

  @Override
  public List<CommentDTO> getAllComments(String promptId) {
    List<Comment> commentList = commentDao.getAllComments(promptId);
    List<CommentDTO> dtoList = commentList.stream().map(comment -> {
      CommentDTO commentDTO = new CommentDTO();
      BeanUtils.copyProperties(comment, commentDTO);
      return commentDTO;
    }).collect(Collectors.toList());
    return dtoList;
  }

}
