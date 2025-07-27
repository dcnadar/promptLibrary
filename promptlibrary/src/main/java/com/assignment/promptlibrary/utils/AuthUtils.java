package com.assignment.promptlibrary.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
  public static String getCurrentUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
