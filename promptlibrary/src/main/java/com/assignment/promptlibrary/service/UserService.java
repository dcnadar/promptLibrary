package com.assignment.promptlibrary.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.UserDao;
import com.assignment.promptlibrary.dto.AuthRequest;
import com.assignment.promptlibrary.dto.JwtResponse;
import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.dto.UserUpdateDTO;
import com.assignment.promptlibrary.exception.UserException;
import com.assignment.promptlibrary.model.User;
import com.assignment.promptlibrary.service.serviceInterfaces.IUserService;
import com.assignment.promptlibrary.utils.RoleValidation;
import com.assignment.promptlibrary.utils.UserMapper;

@Service
public class UserService implements IUserService {

  private UserDao userDao;

  private PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private JwtService jwtService;

  public UserService(UserDao userDao, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
      JwtService jwtService) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @Override
  public void authenticateUser(AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    } catch (Exception e) {
      throw new UserException.UnauthorizedException("Invalid username or password");
    }
  }

  @Override
  public JwtResponse createJwtResponse(String username) {
    String accessToken = jwtService.generateToken(username);
    UserDTO userDTO = getUser(username);
    return new JwtResponse(accessToken, userDTO);
  }

  @Override
  public UserDTO getUser(String username) {
    User user = userDao.findUserByUsername(username);
    if (user == null) {
      throw new UserException.ResourceNotFoundException("User not Found");
    }
    UserDTO userDTO = UserMapper.toDTO(user);
    return userDTO;
  }

  @Override
  public void registerUser(UserDTO userDTO) {

    RoleValidation.validateRole(userDTO.getRole());

    userDao.findUserByEmail(userDTO.getEmail()).ifPresent(u -> {
      throw new UserException.ConflictException("Email already exists");
    });
    if (userDao.findUserByUsername(userDTO.getUsername()) != null) {
      throw new UserException.ConflictException("Username already exists");
    }
    User user = UserMapper.userFromDTO(userDTO);
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

    try {
      userDao.registerUser(user);
    } catch (DataAccessException e) {
      throw new UserException.DatabaseException("Failed to register user: " + e.getMessage());
    }
  }

  @Override
  public UserDTO updateUser(UserUpdateDTO userUpdateDTO, String id) {
    User user = userDao.findUserById(id)
        .orElseThrow(() -> new UserException.ResourceNotFoundException("User not found"));

    UserMapper.updateUserFromDTO(userUpdateDTO, user, passwordEncoder);
    try {
      User updatedUser = userDao.updateUser(user);
      return UserMapper.toDTO(updatedUser);
    } catch (DataAccessException e) {
      throw new UserException.DatabaseException("Failed to Update user: " + e.getMessage());
    }
  }

}
