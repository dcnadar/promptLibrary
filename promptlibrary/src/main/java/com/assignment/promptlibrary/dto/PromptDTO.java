package com.assignment.promptlibrary.dto;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PromptDTO {

  private String promptId;

  @NotBlank(message = "Title is required")
  @Size(max = 100, message = "Title can be up to 100 characters")
  private String title;

  @NotBlank(message = "Description is required")
  private String description;

  @NotNull(message = "Price is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
  private Double price;

  @NotBlank(message = "Category is required")
  private String category;

  @NotBlank(message = "Content type is required")
  private String contentType;

  private List<String> tags;

  private String createdBy;

  private String s3Key;

  private Integer likesCount = 0;

  private Date createdAt = new Date();

  private Date updatedAt = new Date();

  private String fileUrl;

  public PromptDTO() {
  }

  public PromptDTO(String promptId,
      @NotBlank(message = "Title is required") @Size(max = 100, message = "Title can be up to 100 characters") String title,
      @NotBlank(message = "Description is required") String description,
      @NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive") Double price,
      @NotBlank(message = "Category is required") String category,
      @NotBlank(message = "Content type is required") String contentType, List<String> tags, String createdBy,
      Integer likesCount, Date createdAt, Date updatedAt, String fileUrl) {
    this.promptId = promptId;
    this.title = title;
    this.description = description;
    this.price = price;
    this.category = category;
    this.contentType = contentType;
    this.tags = tags;
    this.createdBy = createdBy;
    this.likesCount = likesCount;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.fileUrl = fileUrl;
  }

  public PromptDTO(String promptId,
      @NotBlank(message = "Title is required") @Size(max = 100, message = "Title can be up to 100 characters") String title,
      @NotBlank(message = "Description is required") String description,
      @NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive") Double price,
      @NotBlank(message = "Category is required") String category,
      @NotBlank(message = "Content type is required") String contentType, List<String> tags, String createdBy,
      String s3Key, Integer likesCount, Date createdAt, Date updatedAt, String fileUrl) {
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
    this.fileUrl = fileUrl;
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

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  @Override
  public String toString() {
    return "PromptDTO [promptId=" + promptId + ", title=" + title + ", description=" + description + ", price=" + price
        + ", category=" + category + ", contentType=" + contentType + ", tags=" + tags + ", createdBy=" + createdBy
        + ", s3Key=" + s3Key + ", likesCount=" + likesCount + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
        + ", fileUrl=" + fileUrl + "]";
  }

}
