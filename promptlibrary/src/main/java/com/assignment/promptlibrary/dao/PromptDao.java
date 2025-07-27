package com.assignment.promptlibrary.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Component;

import com.assignment.promptlibrary.model.Prompt;
import com.mongodb.client.result.DeleteResult;

@Component
public class PromptDao {

  private final MongoTemplate mongoTemplate;

  public PromptDao(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public Prompt getPrompt(String promptId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(promptId));
    return mongoTemplate.findOne(query, Prompt.class);
  }

  public void updatePrompt(String promptId, Prompt updatedPrompt, String userId) {
    mongoTemplate.save(updatedPrompt);
  }

  public void deletePrompt(String promptId, String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(promptId).and("createdBy").is(userId));
    DeleteResult result = mongoTemplate.remove(query, Prompt.class);
    System.out.println("Deleted count: " + result.getDeletedCount());
  }

  public List<Prompt> getUserPrompts(String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("createdBy").is(userId));
    return mongoTemplate.find(query, Prompt.class);
  }

  public List<Prompt> getAllPrompts() {
    return mongoTemplate.findAll(Prompt.class);
  }

  public Prompt savePrompt(Prompt prompt) {
    return mongoTemplate.save(prompt);
  }
}
