package com.assignment.promptlibrary.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.dto.UserUpdateDTO;
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
      userDTO.setPassword(null);
      return ResponseEntity.ok(new UserApiResponse(200, "Success", userDTO));
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new UserApiResponse(404, "No record", null));
  }

  @PreAuthorize("hasAuthority('BUYER') or hasAuthority('SELLER')")
  @PutMapping("/users/me")
  public ResponseEntity<UserApiResponse> updateAuthUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {

    String username = AuthUtils.getCurrentUsername();
    UserDTO currentUser = userService.getUser(username);
    if (currentUser == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new UserApiResponse(404, "User not found", null));
    }
    Optional<UserDTO> updatedOpt = userService.updateUser(userUpdateDTO, currentUser.getId());
    if (updatedOpt.isPresent()) {
      updatedOpt.get().setPassword(null);
      return ResponseEntity.ok(new UserApiResponse(200, "Success", updatedOpt.get()));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new UserApiResponse(400, "Update failed", null));
    }
  }

}
