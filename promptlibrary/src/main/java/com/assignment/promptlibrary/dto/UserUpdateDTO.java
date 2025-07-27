package com.assignment.promptlibrary.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {
  private String id;

  @Size(min = 3, max = 20, message = "Username must be 3-20 characters")
  private String username;

  @Email(message = "Email must be valid")
  private String email;

  @Size(min = 6, message = "Password must be at least 6 characters")
  private String password;

  private String role;

  public UserUpdateDTO() {
  }

  public UserUpdateDTO(String id,
      @Size(min = 3, max = 20, message = "Username must be 3-20 characters") String username,
      @Email(message = "Email must be valid") String email,
      @Size(min = 6, message = "Password must be at least 6 characters") String password, String role) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "UserUpdateDTO [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
        + ", role=" + role + "]";
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
