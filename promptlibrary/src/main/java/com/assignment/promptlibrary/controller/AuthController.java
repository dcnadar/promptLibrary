package com.assignment.promptlibrary.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.promptlibrary.dao.UserDao;
import com.assignment.promptlibrary.dto.AuthRequest;
import com.assignment.promptlibrary.dto.JwtResponse;
import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.exception.UserException;
import com.assignment.promptlibrary.model.User;
import com.assignment.promptlibrary.response.UserApiResponse;
import com.assignment.promptlibrary.service.JwtService;
import com.assignment.promptlibrary.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtService jwtService;
  private final UserDao userDao;
  private final UserService userService;

  public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
      JwtService jwtService, UserDao userDao, UserService userService) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
    this.userDao = userDao;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    } catch (Exception e) {
      throw new UserException.UnauthorizedException("Invalid username or password");
    }

    String accessToken = jwtService.generateToken(request.getUsername(), true);
    String refreshToken = jwtService.generateToken(request.getUsername(), false);
    User user = userDao.findUserByUsername(request.getUsername());

    if (user == null) {
      throw new UserException.ResourceNotFoundException("User not found");
    }
    UserDTO userDTO = new UserDTO();
    BeanUtils.copyProperties(user, userDTO);
    userDTO.setPassword(null);
    JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, userDTO);
    return ResponseEntity.ok(jwtResponse);
  }

  @PostMapping("/signup")
  public ResponseEntity<UserApiResponse> registerUser(@Valid @RequestBody UserDTO userDTO) {
    userService.registerUser(userDTO);
    return ResponseEntity.ok(new UserApiResponse(200, "Success"));
  }

}
