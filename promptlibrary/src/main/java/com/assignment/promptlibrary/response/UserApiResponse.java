package com.assignment.promptlibrary.response;

import com.assignment.promptlibrary.dto.UserDTO;

public class UserApiResponse {

  private Integer statusCode;
  private String statusMsg;
  private UserDTO userDTO;

  public UserApiResponse() {
  }

  public UserApiResponse(int statusCode, String statusMsg) {
    this.statusCode = statusCode;
    this.statusMsg = statusMsg;
  }

  public UserApiResponse(Integer statusCode, String statusMsg, UserDTO userDTO) {
    this.statusCode = statusCode;
    this.statusMsg = statusMsg;
    this.userDTO = userDTO;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  public String getStatusMsg() {
    return statusMsg;
  }

  public void setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
  }

  public UserDTO getUserDTO() {
    return userDTO;
  }

  public void setUserDTO(UserDTO userDTO) {
    this.userDTO = userDTO;
  }

  @Override
  public String toString() {
    return "UserApiResponse [statusCode=" + statusCode + ", statusMsg=" + statusMsg + ", userDTO=" + userDTO + "]";
  }

}
