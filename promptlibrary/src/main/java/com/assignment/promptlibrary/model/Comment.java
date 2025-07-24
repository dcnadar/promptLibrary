package com.assignment.promptlibrary.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("comments")
public class Comment {
  @Id
  private String id;

  @Indexed
  private String promptId;

  private String userId;

  private String comment;

  private Date createdAt = new Date();

  public Comment() {
  }

  public Comment(String id, String promptId, String userId, String comment, Date createdAt) {
    this.id = id;
    this.promptId = promptId;
    this.userId = userId;
    this.comment = comment;
    this.createdAt = createdAt;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPromptId() {
    return promptId;
  }

  public void setPromptId(String promptId) {
    this.promptId = promptId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "Comment [id=" + id + ", promptId=" + promptId + ", userId=" + userId + ", comment=" + comment
        + ", createdAt=" + createdAt + "]";
  }

}
