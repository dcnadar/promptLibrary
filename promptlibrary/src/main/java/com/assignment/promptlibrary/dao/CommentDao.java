package com.assignment.promptlibrary.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.assignment.promptlibrary.model.Comment;
import com.assignment.promptlibrary.model.Prompt;
import com.mongodb.client.result.DeleteResult;

public class CommentDao {

  @Autowired
  public MongoTemplate mongoTemplate;

  public Prompt findPrompt(String promptId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("promptId").is(promptId));
    return mongoTemplate.findOne(query, Prompt.class);
  }

  public String addComment(Comment comment, String promptId) {
    // CommentDao commentDao = new CommentDao();
    // Prompt prompt = commentDao.findPrompt(promptId);
    Comment saved = mongoTemplate.save(comment);
    if (saved != null) {
      return "Saved";
    }
    return "Failed to save";
  }

  public List<Comment> getAllComments(String promptId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("promptId").is(promptId));
    return mongoTemplate.find(query, Comment.class);
  }

  public boolean deleteComment(String promptId, String commentId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("promptId").is(promptId).and("commentId").is(commentId));
    DeleteResult result = mongoTemplate.remove(query, Comment.class);
    return result.getDeletedCount() > 0;
  }

}
