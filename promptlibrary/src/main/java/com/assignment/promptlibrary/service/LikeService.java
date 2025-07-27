package com.assignment.promptlibrary.service;

import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.LikeDao;
import com.assignment.promptlibrary.dao.UserDao;
import com.assignment.promptlibrary.exception.LikeException;
import com.assignment.promptlibrary.exception.UserException;
import com.assignment.promptlibrary.model.User;

@Service
public class LikeService {

  private final LikeDao likeDao;
  private final UserDao userDao;

  public LikeService(LikeDao likeDao, UserDao userDao) {
    this.likeDao = likeDao;
    this.userDao = userDao;
  }

  public String likePrompt(String promptId, String username) {
    User user = userDao.findUserByUsername(username);
    if (user == null) {
      throw new UserException.ResourceNotFoundException("User not found");
    }
    boolean result = likeDao.promptLike(promptId, user.getId());
    if (!result) {
      throw new LikeException.ConflictException("Already liked this prompt");
    }
    return "Liked";

  }

  public String unlikePrompt(String promptId, String username) {
    User user = userDao.findUserByUsername(username);
    if (user == null) {
      throw new UserException.ResourceNotFoundException("User not found");
    }
    boolean result = likeDao.promptUnLike(promptId, user.getId());

    if (!result) {
      throw new LikeException.BadRequestException("You haven't liked this prompt yet");
    }
    return "Unliked";

  }

}
