package com.assignment.promptlibrary.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.assignment.promptlibrary.model.Like;
import com.assignment.promptlibrary.model.Prompt;

@Component
public class LikeDao {

  @Autowired
  private MongoTemplate mongoTemplate;

  public boolean promptLike(String promptId, String userId) {
    // find the prompt

    Query likeQuery = new Query();
    likeQuery.addCriteria(Criteria.where("promptId").is(promptId).and("userId").is(userId));
    Like existingLike = mongoTemplate.findOne(likeQuery, Like.class);
    
    if (existingLike != null) {
      return false;
    }

    Like like = new Like();
    like.setPromptId(promptId);
    like.setUserId(userId);
    mongoTemplate.save(like);

    Query promptQuery = new Query();
    promptQuery.addCriteria(Criteria.where("id").is(promptId));
    Update update = new Update().inc("likesCount", 1);
    mongoTemplate.findAndModify(promptQuery, update, Prompt.class);
    return true;

  }

  public boolean promptUnLike(String promptId, String userId) {

    Query likeQuery = new Query();
    likeQuery.addCriteria(Criteria.where("promptId").is(promptId).and("userId").is(userId));
    Like like = mongoTemplate.findOne(likeQuery, Like.class);

    if (like == null) {
      return false;
    }

    mongoTemplate.remove(likeQuery, Like.class);

    Query promptQuery = new Query();
    promptQuery.addCriteria(Criteria.where("id").is(promptId));
    Update update = new Update().inc("likesCount", -1);
    mongoTemplate.findAndModify(promptQuery, update, Prompt.class);
    return true;
  }

}
