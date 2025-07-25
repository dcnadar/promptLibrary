package com.assignment.promptlibrary.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.promptlibrary.dao.PromptDao;
import com.assignment.promptlibrary.dao.UserDao;
import com.assignment.promptlibrary.dto.PromptDTO;
import com.assignment.promptlibrary.model.Prompt;
import com.assignment.promptlibrary.model.User;
import com.assignment.promptlibrary.s3.S3Service;

import jakarta.validation.Valid;

@Service
public class PromptService {
  private final PromptDao promptDao;
  private final UserDao userDao;

  private final S3Service s3Service;

  public PromptService(PromptDao promptDao, UserDao userDao, S3Service s3Service) {
    this.promptDao = promptDao;
    this.userDao = userDao;
    this.s3Service = s3Service;
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

  public PromptDTO updatePrompt(String promptId, PromptDTO promptDTO, MultipartFile file, String username) {

    User userByUsername = userDao.findUserByUsername(username);
    if (userByUsername == null) {
      throw new RuntimeException("User not found");
    }

    Prompt existingPrompt = promptDao.getPrompt(promptId);
    if (existingPrompt == null || !existingPrompt.getCreatedBy().equals(userByUsername.getId())) {
      throw new RuntimeException("Prompt not found or unauthorized");
    }
    String newS3Key = existingPrompt.getS3Key();
    if (file != null && !file.isEmpty()) {
      s3Service.deleteFile(existingPrompt.getS3Key());
      try {
        newS3Key = s3Service.uploadFile(file);
      } catch (IOException e) {
        throw new RuntimeException("File upload failed", e);
      }
    }
    Prompt updatedPrompt = new Prompt();
    BeanUtils.copyProperties(promptDTO, updatedPrompt);
    updatedPrompt.setS3Key(newS3Key);
    updatedPrompt.setUpdatedAt(new Date());

    promptDao.updatePrompt(promptId, updatedPrompt, userByUsername.getId());

    PromptDTO updatedDTO = new PromptDTO();
    BeanUtils.copyProperties(updatedPrompt, updatedDTO);
    return updatedDTO;
  }

  public void deletePrompt(String promptId, String username) {
    User userByUsername = userDao.findUserByUsername(username);
    if (userByUsername == null) {
      throw new RuntimeException("User not found");
    }
    Prompt prompt = promptDao.getPrompt(promptId);
    if (prompt == null || !prompt.getCreatedBy().equals(userByUsername.getId())) {
      throw new RuntimeException("Prompt not found or unauthorized");
    }
    s3Service.deleteFile(prompt.getS3Key());

    promptDao.deletePrompt(promptId, userByUsername.getId());

  }

  public List<PromptDTO> getUserPrompts(String username) {
    User userByUsername = userDao.findUserByUsername(username);
    List<Prompt> userPrompts = promptDao.getUserPrompts(userByUsername.getId());
    return userPrompts.stream().map(prompt -> {
      PromptDTO dto = new PromptDTO();
      BeanUtils.copyProperties(prompt, dto);
      return dto;
    }).collect(Collectors.toList());
  }

  public PromptDTO createPrompt(PromptDTO promptDTO, MultipartFile file, String username) {
    User userByUsername = userDao.findUserByUsername(username);
    if (userByUsername == null) {
      throw new RuntimeException("User not found");
    }
    String s3Key;
    try {
      s3Key = s3Service.uploadFile(file);
    } catch (IOException e) {
      throw new RuntimeException("File upload failed", e);
    }
    Prompt prompt = new Prompt();
    BeanUtils.copyProperties(promptDTO, prompt);
    prompt.setCreatedBy(userByUsername.getId());
    prompt.setS3Key(s3Key);
    prompt.setCreatedAt(new Date());
    prompt.setUpdatedAt(new Date());
    Prompt savedPrompt = promptDao.savePrompt(prompt);
    PromptDTO savedDTO = new PromptDTO();
    BeanUtils.copyProperties(savedPrompt, savedDTO);
    savedDTO.setCreatedBy(userByUsername.getId());

    return savedDTO;

  }

}
