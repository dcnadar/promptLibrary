package com.assignment.promptlibrary.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.UserDao;
import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.dto.UserUpdateDTO;
import com.assignment.promptlibrary.exception.UserException;
import com.assignment.promptlibrary.model.User;
import com.assignment.promptlibrary.service.serviceInterfaces.IUserService;

@Service
public class UserService implements IUserService {

  private UserDao userDao;

  private PasswordEncoder passwordEncoder;

  public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDTO getUser(String username) {
    User userByEmail = userDao.findUserByUsername(username);
    UserDTO userDTO = new UserDTO();
    if (userByEmail != null) {
      BeanUtils.copyProperties(userByEmail, userDTO);
      userDTO.setPassword(null);
      return userDTO;
    }
    return null;
  }

  @Override
  public void registerUser(UserDTO userDTO) {

    String role = userDTO.getRole();
    if (!"BUYER".equalsIgnoreCase(role) && !"SELLER".equalsIgnoreCase(role)) {
      throw new UserException.BadRequestException("Role must be either 'BUYER' or 'SELLER'");
    }
    Optional<User> existingUser = userDao.findUserByEmail(userDTO.getEmail());
    if (existingUser.isPresent()) {
      throw new UserException.ConflictException("Email already exists");
    }
    User existingUsernameUser = userDao.findUserByUsername(userDTO.getUsername());
    if (existingUsernameUser != null) {
      throw new UserException.ConflictException("Username already exists");
    }
    String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
    User userEntity = new User();
    BeanUtils.copyProperties(userDTO, userEntity);
    userEntity.setPassword(hashedPassword);
    userDao.registerUser(userEntity);
  }

  @Override
  public Optional<UserDTO> updateUser(UserUpdateDTO userUpdateDTO, String id) {
    Optional<User> existingUserOpt = userDao.findUserById(id);
    if (existingUserOpt.isEmpty()) {
      return Optional.empty();
    }
    User user = existingUserOpt.get();
    if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
      String hashedPassword = passwordEncoder.encode(userUpdateDTO.getPassword());
      user.setPassword(hashedPassword);
    }
    // Update username if present and not blank
    if (userUpdateDTO.getUsername() != null && !userUpdateDTO.getUsername().isBlank()) {
      user.setUsername(userUpdateDTO.getUsername());
    }

    // Update email if present and not blank
    if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().isBlank()) {
      user.setEmail(userUpdateDTO.getEmail());
    }

    // Update role if present and not blank
    if (userUpdateDTO.getRole() != null && !userUpdateDTO.getRole().isBlank()) {
      user.setRole(userUpdateDTO.getRole());
    }
    Optional<User> updatedUserOpt = userDao.updateUser(user, id);
    if (updatedUserOpt.isPresent()) {
      UserDTO updatedDTO = new UserDTO();
      BeanUtils.copyProperties(updatedUserOpt.get(), updatedDTO);
      updatedDTO.setPassword(null);
      return Optional.of(updatedDTO);
    }
    return Optional.empty();
  }

}
