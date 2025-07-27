package com.assignment.promptlibrary.utils;

import java.util.Set;
import java.util.stream.Collectors;

import com.assignment.promptlibrary.exception.PromptException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ValidationUtils {

  private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  public static <T> void validate(T object) {
    Set<ConstraintViolation<T>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      String errorMsg = violations.stream()
          .map(ConstraintViolation::getMessage)
          .collect(Collectors.joining(", "));
      throw new PromptException.BadRequestException("Validation failed: " + errorMsg);
    }
  }
}
