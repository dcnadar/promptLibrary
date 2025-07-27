package com.assignment.promptlibrary.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

  private String id;

  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 20, message = "Username must be 3-20 characters")
  private String username;

  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 6, message = "Password must be at least 6 characters")
  private String password;

  @NotBlank(message = "Role is required")
  private String role;

  public UserDTO() {
  }

  public UserDTO(String id, String username, String email, String password, String role) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setUsername(String username) {
    this.username = (username != null) ? username.toLowerCase() : null;
  }

  public String getUsername() {
    return username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setRole(String role) {
    this.role = (role != null) ? role.toUpperCase() : null;
  }

  public String getRole() {
    return role;
  }

}