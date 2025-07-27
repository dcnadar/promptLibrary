package com.assignment.promptlibrary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.promptlibrary.dto.AuthRequest;
import com.assignment.promptlibrary.dto.JwtResponse;
import com.assignment.promptlibrary.dto.UserDTO;

import com.assignment.promptlibrary.response.UserApiResponse;

import com.assignment.promptlibrary.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthRequest request) {
    userService.authenticateUser(request);
    JwtResponse jwtResponse = userService.createJwtResponse(request.getUsername());
    return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
  }

  @PostMapping("/signup")
  public ResponseEntity<UserApiResponse> registerUser(@Valid @RequestBody UserDTO userDTO) {
    userService.registerUser(userDTO);
    return ResponseEntity.status(HttpStatus.OK).body(new UserApiResponse("Registration Successfull"));
  }

}
