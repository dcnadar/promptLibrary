package com.assignment.promptlibrary.service.serviceInterfaces;

import java.util.Optional;

import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.dto.UserUpdateDTO;

public interface IUserService {
  UserDTO getUser(String username);

  void registerUser(UserDTO userDTO);

  Optional<UserDTO> updateUser(UserUpdateDTO userUpdateDTO, String id);
}
