package com.assignment.promptlibrary.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

  @Id
  private String id;

  @Indexed(unique = true)
  private String username;

  @Indexed(unique = true)
  private String email;

  private String password;

  private String role;

  public User() {
  }

  public User(String id, String username, String email, String password, String role) {
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

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", role="
        + role + "]";
  }

}
