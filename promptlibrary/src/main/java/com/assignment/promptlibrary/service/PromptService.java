package com.assignment.promptlibrary.service;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.PromptDao;
import com.assignment.promptlibrary.dto.PromptDTO;
import com.assignment.promptlibrary.model.Prompt;

@Service
public class PromptService {
  private final PromptDao promptDao;

  public PromptService(PromptDao promptDao) {
    this.promptDao = promptDao;
  }

  public List<PromptDTO> allPublicPrompts() {
    return promptDao.getAllPrompts().stream().map(prompt -> {
      PromptDTO dto = new PromptDTO();
      BeanUtils.copyProperties(prompt, dto);
      return dto;
    }).collect(Collectors.toList());
  }

  public PromptDTO getPrompt(String promptId) {
    Prompt prompt = promptDao.getPrompt(promptId);
    PromptDTO dto = new PromptDTO();
    BeanUtils.copyProperties(prompt, dto);
    return dto;
  }

  public void updatePrompt(String promptId, PromptDTO promptDTO) {

    Prompt prompt = new Prompt();
    BeanUtils.copyProperties(promptDTO, prompt);
    promptDao.updatePrompt(promptId, prompt);
  }

  public void deletePrompt(String promptId) {
    promptDao.deletePrompt(promptId);
  }

  public List<PromptDTO> getUserPrompts() {
    List<Prompt> userPrompts = promptDao.getUserPrompts();
    return userPrompts.stream().map(prompt -> {
      PromptDTO dto = new PromptDTO();
      BeanUtils.copyProperties(prompt, dto);
      return dto;
    }).collect(Collectors.toList());
  }

}
