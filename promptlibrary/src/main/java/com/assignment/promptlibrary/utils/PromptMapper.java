package com.assignment.promptlibrary.utils;

import com.assignment.promptlibrary.dto.PromptDTO;
import com.assignment.promptlibrary.model.Prompt;

public class PromptMapper {

  public static void updatePromptFromDTO(PromptDTO dto, Prompt entity) {
    if (dto.getTitle() != null)
      entity.setTitle(dto.getTitle());
    if (dto.getDescription() != null)
      entity.setDescription(dto.getDescription());
    if (dto.getPrice() != null)
      entity.setPrice(dto.getPrice());
    if (dto.getCategory() != null)
      entity.setCategory(dto.getCategory());
    if (dto.getContentType() != null)
      entity.setContentType(dto.getContentType());
    if (dto.getTags() != null)
      entity.setTags(dto.getTags());
  }

  public static PromptDTO toDTO(Prompt entity, String fileUrl) {
    PromptDTO dto = new PromptDTO();
    dto.setPromptId(entity.getPromptId());
    dto.setTitle(entity.getTitle());
    dto.setDescription(entity.getDescription());
    dto.setPrice(entity.getPrice());
    dto.setCategory(entity.getCategory());
    dto.setContentType(entity.getContentType());
    dto.setTags(entity.getTags());
    dto.setCreatedBy(entity.getCreatedBy());
    dto.setS3Key(entity.getS3Key());
    dto.setLikesCount(entity.getLikesCount());
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setUpdatedAt(entity.getUpdatedAt());
    dto.setFileUrl(fileUrl);
    return dto;
  }
}
