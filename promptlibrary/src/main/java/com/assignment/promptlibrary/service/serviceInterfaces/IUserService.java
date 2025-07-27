package com.assignment.promptlibrary.service.serviceInterfaces;

import com.assignment.promptlibrary.dto.AuthRequest;
import com.assignment.promptlibrary.dto.JwtResponse;
import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.dto.UserUpdateDTO;

public interface IUserService {

  void authenticateUser(AuthRequest authRequest);

  JwtResponse createJwtResponse(String username);

  UserDTO getUser(String username);

  void registerUser(UserDTO userDTO);

  UserDTO updateUser(UserUpdateDTO userUpdateDTO, String id);
}
