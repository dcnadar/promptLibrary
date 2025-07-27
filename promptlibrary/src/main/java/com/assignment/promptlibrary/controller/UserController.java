package com.assignment.promptlibrary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.dto.UserUpdateDTO;
import com.assignment.promptlibrary.exception.UserException;
import com.assignment.promptlibrary.response.UserApiResponse;
import com.assignment.promptlibrary.service.UserService;
import com.assignment.promptlibrary.utils.AuthUtils;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PreAuthorize("hasAuthority('BUYER') or hasAuthority('SELLER')")
  @GetMapping("/users/me")
  public ResponseEntity<UserApiResponse> getAuthUser() {
    String username = AuthUtils.getCurrentUsername();
    UserDTO userDTO = userService.getUser(username);
    if (userDTO != null) {
      return ResponseEntity.status(HttpStatus.OK).body(new UserApiResponse("Authenticated User Details", userDTO));
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new UserApiResponse("No record"));
  }

  @PreAuthorize("hasAuthority('BUYER') or hasAuthority('SELLER')")
  @PutMapping("/users/me")
  public ResponseEntity<UserApiResponse> updateAuthUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
    String username = AuthUtils.getCurrentUsername();
    UserDTO currentUser = userService.getUser(username);
    if (currentUser == null) {
      throw new UserException.ResourceNotFoundException("User not found");
    }
    UserDTO updatedUser = userService.updateUser(userUpdateDTO, currentUser.getId());
    return ResponseEntity.status(HttpStatus.OK).body(new UserApiResponse("Updated User Details", updatedUser));
  }

}
