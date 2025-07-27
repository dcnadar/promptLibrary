package com.assignment.promptlibrary.utils;

import com.assignment.promptlibrary.exception.PromptException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static <T> T fromJson(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new PromptException.BadRequestException("Invalid JSON: " + e.getOriginalMessage());
    }
  }
}
