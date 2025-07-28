package com.assignment.promptlibrary.response;

import java.util.List;

import com.assignment.promptlibrary.dto.PromptDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromptApiResponse {
  private String statusMsg;
  private PromptDTO dto;
  private List<PromptDTO> dtoList;

  public PromptApiResponse() {
  }

  public PromptApiResponse(String statusMsg) {
    this.statusMsg = statusMsg;
  }

  public PromptApiResponse(String statusMsg, PromptDTO dto) {
    this.statusMsg = statusMsg;
    this.dto = dto;
  }

  public PromptApiResponse(String statusMsg, List<PromptDTO> dtoList) {
    this.statusMsg = statusMsg;
    this.dtoList = dtoList;
  }

  public String getStatusMsg() {
    return statusMsg;
  }

  public void setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
  }

  public PromptDTO getDto() {
    return dto;
  }

  public void setDto(PromptDTO dto) {
    this.dto = dto;
  }

  public List<PromptDTO> getDtoList() {
    return dtoList;
  }

  public void setDtoList(List<PromptDTO> dtoList) {
    this.dtoList = dtoList;
  }

  @Override
  public String toString() {
    return "PromptApiResponse [statusMsg=" + statusMsg + ", dto=" + dto + "]";
  }
}
