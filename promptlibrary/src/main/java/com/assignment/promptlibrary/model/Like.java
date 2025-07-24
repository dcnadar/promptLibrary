package com.assignment.promptlibrary.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("likes")
@CompoundIndex(def = "{'promptId': 1, 'userId': 1}", unique = true)
public class Like {
  @Id
  private String id;

  @Indexed
  private String promptId;

  private String userId;

  public Like() {
  }

  public Like(String id, String promptId, String userId) {
    this.id = id;
    this.promptId = promptId;
    this.userId = userId;
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

}
