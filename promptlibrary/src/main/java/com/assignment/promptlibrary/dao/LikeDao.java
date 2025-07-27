package com.assignment.promptlibrary.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.assignment.promptlibrary.exception.LikeException;
import com.assignment.promptlibrary.model.Like;
import com.assignment.promptlibrary.model.Prompt;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.result.DeleteResult;

@Component
public class LikeDao {

  @Autowired
  private MongoTemplate mongoTemplate;

  // @Autowired
  // private PromptDao promptDao;

  public boolean promptLike(String promptId, String userId) {
    Query promptQuery = new Query();
    promptQuery.addCriteria(Criteria.where("_id").is(promptId));
    boolean promptExists = mongoTemplate.exists(promptQuery, Prompt.class);
    if (!promptExists) {
      throw new LikeException.ResourceNotFoundException("Prompt not found");
    }
    Query likeQuery = new Query(Criteria.where("promptId").is(promptId).and("userId").is(userId));
    boolean alreadyLiked = mongoTemplate.exists(likeQuery, Like.class);
    if (alreadyLiked) {
      throw new LikeException.ConflictException("Already liked this prompt");
    }
    Like like = new Like();
    like.setPromptId(promptId);
    like.setUserId(userId);
    mongoTemplate.insert(like);
    Update update = new Update().inc("likesCount", 1);
    mongoTemplate.findAndModify(promptQuery, update, Prompt.class);
    return true;
  }

  public boolean promptUnLike(String promptId, String userId) {
    Query promptQuery = new Query();
    promptQuery.addCriteria(Criteria.where("_id").is(promptId));
    boolean promptExists = mongoTemplate.exists(promptQuery, Prompt.class);
    if (!promptExists) {
      throw new LikeException.ResourceNotFoundException("Prompt not found");
    }
    Query likeQuery = Query.query(
        Criteria.where("promptId").is(promptId).and("userId").is(userId));
    DeleteResult result = mongoTemplate.remove(likeQuery, Like.class);

    if (result.getDeletedCount() == 0) {
      return false;
    }
    Query safeDecrementQuery = new Query(Criteria.where("_id").is(promptId).and("likesCount").gt(0));
    Update update = new Update().inc("likesCount", -1);
    mongoTemplate.findAndModify(safeDecrementQuery, update, Prompt.class);
    return true;
  }

}
