package com.assignment.promptlibrary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.promptlibrary.dao.UserDao;
import com.assignment.promptlibrary.dto.PromptDTO;
import com.assignment.promptlibrary.model.Prompt;
import com.assignment.promptlibrary.model.User;
import com.assignment.promptlibrary.service.PromptService;
import com.assignment.promptlibrary.service.UserService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class PromptController {

  private final PromptService promptService;
  // private S3Service s3Service;

  public PromptController(PromptService promptService) {// , S3Service s3Service) {
    this.promptService = promptService;
    // this.s3Service = s3Service;
  }

  /*
   * Upload a prompt with metadata + preview file (multipart).
   * Upload file to AWS S3, store metadata + file reference in prompts collection.
   */
  @PostMapping(value = "/prompts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<PromptDTO> createPrompt(
      @RequestPart("metadata") @Valid PromptDTO promptDTO,
      @RequestPart("file") MultipartFile file) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    PromptDTO createdPrompt = promptService.createPrompt(promptDTO, file, username);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPrompt);
  }

  // List all public prompts
  @GetMapping("/prompts")
  public ResponseEntity<List<PromptDTO>> getPublicPrompts() {
    List<PromptDTO> list = promptService.allPublicPrompts();
    return ResponseEntity.status(HttpStatus.OK).body(list);
  }

  // View details of a single prompt (including preview file link and metadata
  @GetMapping("/prompts/{promptId}")
  public ResponseEntity<PromptDTO> getPrompt(@PathVariable String promptId) {
    PromptDTO promptDTO = promptService.getPrompt(promptId);
    return ResponseEntity.status(HttpStatus.OK).body(promptDTO);
  }

  /*
   * Update a prompt’s metadata or preview file.
   * Only the owner can update. If replacing file, old file must be deleted from
   * S3
   */
  @PutMapping(value = "/prompts/{promptId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> updatePrompt(
      @PathVariable String promptId,
      @RequestPart("metadata") PromptDTO promptDTO,
      @RequestPart(value = "file", required = false) MultipartFile file) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    PromptDTO updated = promptService.updatePrompt(promptId, promptDTO, file, username);
    return ResponseEntity.status(HttpStatus.OK).body(updated);
  }

  /*
   * Delete a prompt and its associated preview file from S3.
   * Only theowner can delete
   */
  @DeleteMapping("/prompts/{promptId}")
  public ResponseEntity<?> deletePrompt(@PathVariable String promptId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    promptService.deletePrompt(promptId, username);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  // List all prompts created by the authenticated user (Prompt Library Studio)
  @GetMapping("/users/me/prompts")
  public ResponseEntity<List<PromptDTO>> getUserPrompts() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    List<PromptDTO> userPrompts = promptService.getUserPrompts(username);
    return ResponseEntity.status(HttpStatus.OK).body(userPrompts);
  }

}
