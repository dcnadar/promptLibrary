package com.assignment.promptlibrary.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.assignment.promptlibrary.model.Prompt;

@Component
public class PromptDao {

  private final MongoTemplate mongoTemplate;

  public PromptDao(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public Prompt getPrompt(String promptId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(promptId));
    return mongoTemplate.findOne(query, Prompt.class);
  }

  public void updatePrompt(String promptId, Prompt updatedPrompt, String userId) {
    Query query = new Query();

    query.addCriteria(Criteria.where("id").is(promptId).and("createdBy").is(userId));
    Update update = new Update()
        .set("title", updatedPrompt.getTitle())
        .set("description", updatedPrompt.getDescription())
        .set("price", updatedPrompt.getPrice())
        .set("category", updatedPrompt.getCategory())
        .set("contentType", updatedPrompt.getContentType())
        .set("tags", updatedPrompt.getTags())
        .set("s3Key", updatedPrompt.getS3Key())
        .set("updatedAt", new Date());

    mongoTemplate.findAndModify(query, update, Prompt.class);
  }

  public void deletePrompt(String promptId, String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(promptId).and("createdBy").is(userId));
    mongoTemplate.remove(query, Prompt.class);
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
