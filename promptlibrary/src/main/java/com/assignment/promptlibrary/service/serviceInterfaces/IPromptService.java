package com.assignment.promptlibrary.service.serviceInterfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.assignment.promptlibrary.dto.PromptDTO;

public interface IPromptService {
  List<PromptDTO> allPublicPrompts();

  PromptDTO getPrompt(String promptId);

  PromptDTO updatePrompt(String promptId, PromptDTO promptDTO, MultipartFile file, String username);

  void deletePrompt(String promptId, String username);

  List<PromptDTO> getUserPrompts(String username);

  PromptDTO createPrompt(PromptDTO promptDTO, MultipartFile file, String username);
}
