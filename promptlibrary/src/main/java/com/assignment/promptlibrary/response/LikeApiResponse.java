package com.assignment.promptlibrary.response;

public class LikeApiResponse {
  private String message;

  public LikeApiResponse() {
  }

  public LikeApiResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "LikeApiResponse [message=" + message + "]";
  }

}
