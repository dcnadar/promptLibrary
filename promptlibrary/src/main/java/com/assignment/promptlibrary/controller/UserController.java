package com.assignment.promptlibrary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.promptlibrary.dto.UserDTO;
import com.assignment.promptlibrary.response.UserApiResponse;
import com.assignment.promptlibrary.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  // REGISTER
  @PostMapping("/auth/signup")
  public ResponseEntity<UserApiResponse> registerUser(@RequestBody UserDTO userDTO) {
    int status = userService.registerUser(userDTO);
    if (status == 200) {
      return ResponseEntity.ok(new UserApiResponse(200, "Success", userDTO));
    }
    return ResponseEntity.ok(new UserApiResponse(409, "Email Already Exists", userDTO));
  }

  // LOG IN
  @PostMapping("/auth/login")
  public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
    boolean res = userService.authenticateUser(userDTO);
    if (res) {
      return ResponseEntity.ok(new UserApiResponse(200, "Success", userDTO));
    }
    return ResponseEntity.ok(new UserApiResponse(409, "Authenticatoin Failed", userDTO));
  }

  // User Profile - authenticated need to check jwt token
  @GetMapping("/users/me")
  public ResponseEntity<?> getAuthUser(@RequestParam String email) {
    UserDTO userDTO = userService.getUser(email);
    if (userDTO != null) {
      return ResponseEntity.ok(new UserApiResponse(200, "Success", userDTO));
    }
    return ResponseEntity.ok(new UserApiResponse(200, "No record", userDTO));
  }

  // Update User - authenticated need to check jwt token

  @PutMapping("/users/me")
  public ResponseEntity<UserApiResponse> updateAuthUser(@RequestBody UserDTO userDTO, @RequestParam String id) {
    Boolean result = userService.updateUser(userDTO, id);
    if (result != false) {
      return ResponseEntity.ok(new UserApiResponse(200, "success", userDTO));
    }
    return ResponseEntity.ok(new UserApiResponse(202, "Operation Failed", userDTO));

  }
}
