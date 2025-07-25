package com.assignment.promptlibrary.service;

import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.LikeDao;
import com.assignment.promptlibrary.dao.UserDao;
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
    boolean res = likeDao.promptLike(promptId, user.getId());
    return res ? "Liked" : "Already Liked or Failed";

  }

  public String unlikePrompt(String promptId, String username) {
    User user = userDao.findUserByUsername(username);
    boolean res = likeDao.promptUnLike(promptId, user.getId());
    return res ? "Unliked" : "Not Liked or Failed";

  }

}
