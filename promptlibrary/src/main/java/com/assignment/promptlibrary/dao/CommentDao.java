package com.assignment.promptlibrary.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.assignment.promptlibrary.model.Comment;

import com.mongodb.client.result.DeleteResult;

@Component
public class CommentDao {

  @Autowired
  public MongoTemplate mongoTemplate;

  public Comment findCommentById(String commentId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(commentId));
    return mongoTemplate.findOne(query, Comment.class);
  }

  public List<Comment> getAllComments(String promptId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("promptId").is(promptId));
    return mongoTemplate.find(query, Comment.class);
  }

  public boolean addComment(Comment comment) {
    Comment saved = mongoTemplate.save(comment);
    return saved != null;
  }

  public boolean deleteCommentById(String commentId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(commentId));
    DeleteResult result = mongoTemplate.remove(query, Comment.class);
    return result.getDeletedCount() > 0;
  }

}
