package com.assignment.promptlibrary.response;

import com.assignment.promptlibrary.dto.UserDTO;

public class UserApiResponse {

  private String statusMsg;
  private UserDTO userDTO;

  public UserApiResponse() {
  }

  public UserApiResponse(String statusMsg) {

    this.statusMsg = statusMsg;
  }

  public UserApiResponse(String statusMsg, UserDTO userDTO) {

    this.statusMsg = statusMsg;
    this.userDTO = userDTO;
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
    return "UserApiResponse [ statusMsg=" + statusMsg + ", userDTO=" + userDTO + "]";
  }

}
