package com.assignment.promptlibrary.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.UserDao;
import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.model.User;

@Service
public class UserService {

  private UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  // registering a new user
  public int registerUser(UserDTO userDTO) {
    Optional<User> existingUser = userDao.findUserByEmail(userDTO.getEmail());
    if (existingUser.isPresent()) {
      return 409;
    }
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
    User userEntity = new User();
    BeanUtils.copyProperties(userDTO, userEntity);
    userEntity.setPassword(hashedPassword);
    userDao.registerUser(userEntity);
    return 200;
  }

  // issue jwt token in this
  public boolean authenticateUser(UserDTO userDTO) {
    String emailId = userDTO.getEmail();
    Optional<User> userByEmail = userDao.findUserByEmail(emailId);
    if (userByEmail.isPresent()) {
      User user = userByEmail.get();
      String hashedPassword = user.getPassword();
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      Boolean isAvilable = bCryptPasswordEncoder.matches(userDTO.getPassword(), hashedPassword);
      return isAvilable;
    }
    return false;
  }

  // info of a user
  public UserDTO getUser(String email) {
    Optional<User> userByEmail = userDao.findUserByEmail(email);
    if (userByEmail.isPresent()) {
      User user = userByEmail.get();
      UserDTO userDTO = new UserDTO();
      BeanUtils.copyProperties(user, userDTO);

      return userDTO;
    }

    return null;
  }

  // update profile data
  public boolean updateUser(UserDTO userDTO, String id) {
    Optional<User> existingUser = userDao.findUserById(id);
    if (existingUser.isPresent()) {
      User user = existingUser.get();
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      String hashedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
      BeanUtils.copyProperties(userDTO, user);
      user.setPassword(hashedPassword);
      Boolean updateduser = userDao.updateUser(user, id);
      return updateduser;
    }

    return false;

  }

}
