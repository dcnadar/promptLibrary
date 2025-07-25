package com.assignment.promptlibrary.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.UserDao;
import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.model.User;

@Service
public class UserService {

  private UserDao userDao;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  // registering a new user
  public Optional<UserDTO> registerUser(UserDTO userDTO) {
    Optional<User> existingUser = userDao.findUserByEmail(userDTO.getEmail());
    if (existingUser.isPresent()) {
      return Optional.empty();
    }
    String hashedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
    User userEntity = new User();
    BeanUtils.copyProperties(userDTO, userEntity);
    userEntity.setPassword(hashedPassword);
    User savedUser = userDao.registerUser(userEntity);
    UserDTO responseDto = new UserDTO();
    BeanUtils.copyProperties(savedUser, responseDto);
    responseDto.setPassword(null);
    responseDto.setPassword(null);
    return Optional.of(responseDto);
  }

  // info of a user
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

  // update profile data
  public Optional<UserDTO> updateUser(UserDTO userDTO, String id) {
    Optional<User> existingUserOpt = userDao.findUserById(id);
    if (existingUserOpt.isEmpty()) {
      return Optional.empty();
    }
    User user = existingUserOpt.get();
    if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
      String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
      user.setPassword(hashedPassword);
    }
    user.setUsername(userDTO.getUsername());
    // user.setEmail(userDTO.getEmail());
    // user.setRole(userDTO.getRole());
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
