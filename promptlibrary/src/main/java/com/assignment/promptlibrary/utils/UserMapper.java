package com.assignment.promptlibrary.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.dto.UserUpdateDTO;
import com.assignment.promptlibrary.model.User;

public class UserMapper {

  public static void updateUserFromDTO(UserUpdateDTO userUpdateDTO, User user) {
    if (userUpdateDTO.getUsername() != null && !userUpdateDTO.getUsername().isBlank()) {
      user.setUsername(userUpdateDTO.getUsername());
    }

    if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().isBlank()) {
      user.setEmail(userUpdateDTO.getEmail());
    }

    if (userUpdateDTO.getRole() != null && !userUpdateDTO.getRole().isBlank()) {
      user.setRole(userUpdateDTO.getRole());
    }
  }

  public static void updateUserFromDTO(UserUpdateDTO userUpdateDTO, User user, PasswordEncoder encoder) {

    updateUserFromDTO(userUpdateDTO, user);

    if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
      user.setPassword(encoder.encode(userUpdateDTO.getPassword()));
    }
  }

  public static UserDTO toDTO(User user) {
    UserDTO dto = new UserDTO();
    BeanUtils.copyProperties(user, dto);
    dto.setPassword(null);
    return dto;
  }

  public static User userFromDTO(UserDTO userDTO) {
    User user = new User();
    BeanUtils.copyProperties(userDTO, user);
    return user;
  }
}
