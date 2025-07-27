package com.assignment.promptlibrary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.promptlibrary.dto.PromptDTO;
import com.assignment.promptlibrary.response.PromptApiResponse;
import com.assignment.promptlibrary.service.PromptService;
import com.assignment.promptlibrary.utils.AuthUtils;
import com.assignment.promptlibrary.utils.JsonUtils;
import com.assignment.promptlibrary.utils.ValidationUtils;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class PromptController {

  private final PromptService promptService;

  public PromptController(PromptService promptService) {
    this.promptService = promptService;

  }

  @PreAuthorize("hasAuthority('SELLER')")
  @PostMapping(value = "/prompts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<PromptApiResponse> createPrompt(
      @RequestPart("metadata") String metadata,
      @RequestPart("file") MultipartFile file) {
    PromptDTO promptDTO = JsonUtils.fromJson(metadata, PromptDTO.class);
    ValidationUtils.validate(promptDTO);
    String username = AuthUtils.getCurrentUsername();
    PromptDTO createdPrompt = promptService.createPrompt(promptDTO, file, username);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new PromptApiResponse("Prompt Creation Success", createdPrompt));
  }

  @GetMapping("/prompts")
  public ResponseEntity<PromptApiResponse> getPublicPrompts() {
    List<PromptDTO> list = promptService.allPublicPrompts();
    return ResponseEntity.status(HttpStatus.OK).body(new PromptApiResponse("Prompt List", list));
  }

  @GetMapping("/prompts/{promptId}")
  public ResponseEntity<PromptApiResponse> getPrompt(@PathVariable String promptId) {
    PromptDTO promptDTO = promptService.getPrompt(promptId);
    return ResponseEntity.status(HttpStatus.OK).body(new PromptApiResponse("Request Success", promptDTO));
  }

  @PreAuthorize("hasAuthority('SELLER')")
  @PutMapping(value = "/prompts/{promptId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<PromptApiResponse> updatePrompt(
      @PathVariable String promptId,
      @RequestPart("metadata") String metadata,
      @RequestPart(value = "file", required = false) MultipartFile file) {
    PromptDTO promptDTO = JsonUtils.fromJson(metadata, PromptDTO.class);
    String username = AuthUtils.getCurrentUsername();
    PromptDTO updated = promptService.updatePrompt(promptId, promptDTO, file, username);
    return ResponseEntity.status(HttpStatus.OK).body(new PromptApiResponse("Prompt Updated Successfully", updated));

  }

  @PreAuthorize("hasAuthority('SELLER')")
  @DeleteMapping("/prompts/{promptId}")
  public ResponseEntity<PromptApiResponse> deletePrompt(@PathVariable String promptId) {

    String username = AuthUtils.getCurrentUsername();
    promptService.deletePrompt(promptId, username);
    return ResponseEntity.status(HttpStatus.OK).body(new PromptApiResponse("Prompt Deleted Successfully"));
  }

  @PreAuthorize("hasAuthority('SELLER')")
  @GetMapping("/users/me/prompts")
  public ResponseEntity<PromptApiResponse> getUserPrompts() {

    String username = AuthUtils.getCurrentUsername();
    List<PromptDTO> userPrompts = promptService.getUserPrompts(username);
    return ResponseEntity.status(HttpStatus.OK).body(new PromptApiResponse("All User Prompts Fetched", userPrompts));
  }

}
