package com.assignment.promptlibrary.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("prompts")
public class Prompt {

  @Id
  private String promptId;
  private String title;
  private String description;
  private Double price;
  private String category;
  private String contentType;
  private List<String> tags;

  @Indexed
  private String createdBy;
  private String s3Key;
  private Integer likesCount = 0;
  private Date createdAt = new Date();
  private Date updatedAt = new Date();

  public Prompt() {
  }

  public Prompt(String promptId, String title, String description, Double price, String category, String contentType,
      List<String> tags, String createdBy, String s3Key, Integer likesCount, Date createdAt, Date updatedAt) {
    this.promptId = promptId;
    this.title = title;
    this.description = description;
    this.price = price;
    this.category = category;
    this.contentType = contentType;
    this.tags = tags;
    this.createdBy = createdBy;
    this.s3Key = s3Key;
    this.likesCount = likesCount;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public String getPromptId() {
    return promptId;
  }

  public void setPromptId(String promptId) {
    this.promptId = promptId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getS3Key() {
    return s3Key;
  }

  public void setS3Key(String s3Key) {
    this.s3Key = s3Key;
  }

  public Integer getLikesCount() {
    return likesCount;
  }

  public void setLikesCount(Integer likesCount) {
    this.likesCount = likesCount;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "Prompt [promptId=" + promptId + ", title=" + title + ", description=" + description + ", price=" + price
        + ", category=" + category + ", contentType=" + contentType + ", tags=" + tags + ", createdBy=" + createdBy
        + ", s3Key=" + s3Key + ", likesCount=" + likesCount + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
        + "]";
  }

}
