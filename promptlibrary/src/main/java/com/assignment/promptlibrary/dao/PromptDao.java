package com.assignment.promptlibrary.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.assignment.promptlibrary.model.Prompt;
import com.assignment.promptlibrary.model.User;

@Component
public class PromptDao {

  private final MongoTemplate mongoTemplate;

  public PromptDao(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public List<Prompt> getAllPrompts() {
    return mongoTemplate.findAll(Prompt.class);
  }

  public Prompt getPrompt(String promptId) {
    Query query = new Query();
    query.addCriteria(Criteria.where(promptId).is(promptId));
    return mongoTemplate.findOne(query, Prompt.class);

  }

  public void updatePrompt(String promptId, Prompt prompt) {
    Query query = new Query();
    User user = new User();
    query.addCriteria(Criteria.where("promptId").is(promptId).and("userId").is(user.getId()));
    Update update = new Update();
    update.set(promptId, update);
    mongoTemplate.findAndModify(query, null, null);
  }

  public void deletePrompt(String promptId) {
    Query query = new Query();
    User user = new User();
    query.addCriteria(Criteria.where("promptId").is(promptId).and("userId").is(user.getId()));
    mongoTemplate.remove(null, Prompt.class);
  }

  public List<Prompt> getUserPrompts() {
    Query query = new Query();
    User user = new User();
    query.addCriteria(Criteria.where("userId").is(user.getId()));
    List<Prompt> list = mongoTemplate.find(query, Prompt.class);
    return list;
  }

}
