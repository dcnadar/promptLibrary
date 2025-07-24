package com.assignment.promptlibrary.service;

import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.LikeDao;

@Service
public class LikeService {

  private final LikeDao likeDao;

  public LikeService(LikeDao likeDao) {
    this.likeDao = likeDao;
  }

  public String likePrompt(String promptId) {
    // String pid = String.valueOf(promptId);
    boolean res = likeDao.promptLike(promptId);
    if (res) {
      return "Liked";
    }
    return "";

  }

  public String unlikePrompt(String promptId) {
    boolean res = likeDao.promptUnLike(promptId);
    if (res) {
      return "UnLiked";
    }
    return "";

  }

}
