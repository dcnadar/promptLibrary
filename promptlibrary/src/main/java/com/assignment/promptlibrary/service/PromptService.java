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
import com.assignment.promptlibrary.exception.PromptException;
import com.assignment.promptlibrary.exception.UserException;
import com.assignment.promptlibrary.model.Prompt;
import com.assignment.promptlibrary.model.User;
import com.assignment.promptlibrary.s3.S3Service;
import com.assignment.promptlibrary.service.serviceInterfaces.IPromptService;
import com.assignment.promptlibrary.utils.PromptMapper;

@Service
public class PromptService implements IPromptService {
  private final PromptDao promptDao;
  private final UserDao userDao;

  private final S3Service s3Service;

  public PromptService(PromptDao promptDao, UserDao userDao, S3Service s3Service) {
    this.promptDao = promptDao;
    this.userDao = userDao;
    this.s3Service = s3Service;
  }

  @Override
  public PromptDTO createPrompt(PromptDTO promptDTO, MultipartFile file, String username) {
    User userByUsername = userDao.findUserByUsername(username);
    if (userByUsername == null) {
      throw new UserException.ResourceNotFoundException("User not found");
    }
    String s3Key;
    try {
      s3Key = s3Service.uploadFile(file);
    } catch (IOException e) {
      throw new PromptException.BadRequestException("File upload failed");
    }
    Prompt prompt = new Prompt();
    BeanUtils.copyProperties(promptDTO, prompt);
    prompt.setCreatedBy(userByUsername.getId());
    prompt.setS3Key(s3Key);
    prompt.setCreatedAt(new Date());
    prompt.setUpdatedAt(new Date());
    Prompt savedPrompt = promptDao.savePrompt(prompt);
    String fileUrl = s3Service.getFileUrl(s3Key);
    PromptDTO savedDTO = PromptMapper.toDTO(savedPrompt, fileUrl);
    System.out.println(savedDTO);
    return savedDTO;
  }

  @Override
  public List<PromptDTO> allPublicPrompts() {
    return promptDao.getAllPrompts().stream().map(prompt -> {
      PromptDTO dto = new PromptDTO();
      BeanUtils.copyProperties(prompt, dto);
      return dto;
    }).collect(Collectors.toList());
  }

  @Override
  public PromptDTO getPrompt(String promptId) {
    Prompt prompt = promptDao.getPrompt(promptId);
    if (prompt == null) {
      throw new PromptException.ResourceNotFoundException("Prompt not found");
    }
    PromptDTO dto = new PromptDTO();

    String fileUrl = s3Service.getFileUrl(prompt.getS3Key());
    BeanUtils.copyProperties(prompt, dto);
    dto.setFileUrl(fileUrl);
    return dto;
  }

  @Override
  public PromptDTO updatePrompt(String promptId, PromptDTO promptDTO, MultipartFile file, String username) {
    User userByUsername = userDao.findUserByUsername(username);
    if (userByUsername == null) {
      throw new UserException.ResourceNotFoundException("User not found");
    }
    Prompt existingPrompt = promptDao.getPrompt(promptId);
    if (existingPrompt == null) {
      throw new PromptException.ResourceNotFoundException("Prompt not found");
    }
    if (!existingPrompt.getCreatedBy().equals(userByUsername.getId())) {
      throw new PromptException.UnauthorizedException("You are not the owner of this prompt");
    }
    String newS3Key = existingPrompt.getS3Key();

    PromptMapper.updatePromptFromDTO(promptDTO, existingPrompt);

    if (file != null && !file.isEmpty()) {
      try {
        s3Service.deleteFile(existingPrompt.getS3Key());
        newS3Key = s3Service.uploadFile(file);
        existingPrompt.setS3Key(newS3Key);
      } catch (IOException e) {
        throw new PromptException.BadRequestException("Failed to upload new file");
      }
    }
    existingPrompt.setUpdatedAt(new Date());
    promptDao.updatePrompt(existingPrompt);
    String fileUrl = s3Service.getFileUrl(existingPrompt.getS3Key());
    PromptDTO updatedDTO = PromptMapper.toDTO(existingPrompt, fileUrl);
    return updatedDTO;
  }

  @Override
  public void deletePrompt(String promptId, String username) {
    User user = userDao.findUserByUsername(username);
    if (user == null) {
      throw new UserException.ResourceNotFoundException("User not found");
    }
    Prompt prompt = promptDao.getPrompt(promptId);
    if (prompt == null) {
      throw new PromptException.ResourceNotFoundException("Prompt not found");
    }
    if (!prompt.getCreatedBy().equals(user.getId())) {
      throw new PromptException.UnauthorizedException("You are not the owner of this prompt");
    }
    s3Service.deleteFile(prompt.getS3Key());
    promptDao.deletePrompt(promptId, user.getId());
  }

  @Override
  public List<PromptDTO> getUserPrompts(String username) {
    User user = userDao.findUserByUsername(username);
    if (user == null) {
      throw new UserException.ResourceNotFoundException("User not found");
    }
    List<Prompt> userPrompts = promptDao.getUserPrompts(user.getId());
    return userPrompts.stream().map(prompt -> {
      return PromptMapper.toDTO(prompt);
    }).collect(Collectors.toList());
  }

}
