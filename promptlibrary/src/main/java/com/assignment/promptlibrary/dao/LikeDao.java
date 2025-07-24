package com.assignment.promptlibrary.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.assignment.promptlibrary.model.Prompt;

@Component
public class LikeDao {

  @Autowired
  private MongoTemplate mongoTemplate;

  public boolean promptLike(String promptId) {
    // find the prompt
    LikeDao likeDao = new LikeDao();
    Prompt prompt = likeDao.findPrompt(promptId);
    if (prompt == null) {
      return false;
    }
    // query to increse the count
    Query query = new Query();
    query.addCriteria(Criteria.where("promptId").is(promptId));
    Update update = new Update();
    Integer likesCount = prompt.getLikesCount();
    likesCount = likesCount + 1;
    update.set("likesCount", likesCount);
    mongoTemplate.findAndModify(query, update, Prompt.class);
    return true;
  }

  public boolean promptUnLike(String promptId) {
    // find the prompt
    LikeDao likeDao = new LikeDao();
    Prompt prompt = likeDao.findPrompt(promptId);
    if (prompt == null) {
      return false;
    }
    // query to increse the count
    Query query = new Query();
    query.addCriteria(Criteria.where("promptId").is(promptId));
    Update update = new Update();
    Integer likesCount = prompt.getLikesCount();
    if (likesCount == 0) {
      return false;
    }
    likesCount = likesCount - 1;
    update.set("likesCount", likesCount);
    mongoTemplate.findAndModify(query, update, Prompt.class);
    return true;
  }

  public Prompt findPrompt(String promptId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("promptId").is(promptId));
    return mongoTemplate.findOne(query, Prompt.class);
  }

}
