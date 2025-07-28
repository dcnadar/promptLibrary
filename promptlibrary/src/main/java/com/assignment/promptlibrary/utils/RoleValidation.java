package com.assignment.promptlibrary.utils;

import com.assignment.promptlibrary.exception.UserException;

public class RoleValidation {

  public static void validateRole(String role) {
    if (!"BUYER".equalsIgnoreCase(role) && !"SELLER".equalsIgnoreCase(role)) {
      throw new UserException.BadRequestException("Role must be either 'BUYER' or 'SELLER'");
    }
  }

}
