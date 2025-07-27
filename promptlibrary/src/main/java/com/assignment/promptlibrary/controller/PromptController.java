package com.assignment.promptlibrary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.promptlibrary.dto.PromptDTO;
import com.assignment.promptlibrary.exception.PromptException;
import com.assignment.promptlibrary.service.PromptService;
import com.assignment.promptlibrary.utils.AuthUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
  public ResponseEntity<PromptDTO> createPrompt(
      @RequestPart("metadata") String metadataJson,
      @RequestPart("file") MultipartFile file) {
    ObjectMapper objectMapper = new ObjectMapper();
    PromptDTO promptDTO = new PromptDTO();
    try {
      promptDTO = objectMapper.readValue(metadataJson, PromptDTO.class);
    } catch (JsonMappingException e) {
      throw new PromptException.BadRequestException("Json mapping exception");
    } catch (JsonProcessingException e) {
      throw new PromptException.BadRequestException("Json Processing exception");
    }
    String username = AuthUtils.getCurrentUsername();
    PromptDTO createdPrompt = promptService.createPrompt(promptDTO, file, username);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPrompt);
  }

  @GetMapping("/prompts")
  public ResponseEntity<List<PromptDTO>> getPublicPrompts() {
    List<PromptDTO> list = promptService.allPublicPrompts();
    return ResponseEntity.status(HttpStatus.OK).body(list);
  }

  @GetMapping("/prompts/{promptId}")
  public ResponseEntity<PromptDTO> getPrompt(@PathVariable String promptId) {
    PromptDTO promptDTO = promptService.getPrompt(promptId);
    return ResponseEntity.status(HttpStatus.OK).body(promptDTO);
  }

  @PreAuthorize("hasAuthority('SELLER')")
  @PutMapping(value = "/prompts/{promptId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> updatePrompt(
      @PathVariable String promptId,
      @RequestPart("metadata") String metadata,
      @RequestPart(value = "file", required = false) MultipartFile file) {

    ObjectMapper objectMapper = new ObjectMapper();
    PromptDTO promptDTO = new PromptDTO();
    try {
      promptDTO = objectMapper.readValue(metadata, PromptDTO.class);
    } catch (JsonMappingException e) {
      throw new PromptException.BadRequestException("Json mapping exception");
    } catch (JsonProcessingException e) {
      throw new PromptException.BadRequestException("Json Processing exception");
    }
    String username = AuthUtils.getCurrentUsername();
    PromptDTO updated = promptService.updatePrompt(promptId, promptDTO, file, username);
    return ResponseEntity.status(HttpStatus.OK).body(updated);
  }

  @PreAuthorize("hasAuthority('SELLER')")
  @DeleteMapping("/prompts/{promptId}")
  public ResponseEntity<?> deletePrompt(@PathVariable String promptId) {

    String username = AuthUtils.getCurrentUsername();
    promptService.deletePrompt(promptId, username);
    return ResponseEntity.ok("Prompt deleted successfully");
  }

  @PreAuthorize("hasAuthority('SELLER')")
  @GetMapping("/users/me/prompts")
  public ResponseEntity<List<PromptDTO>> getUserPrompts() {

    String username = AuthUtils.getCurrentUsername();
    List<PromptDTO> userPrompts = promptService.getUserPrompts(username);
    return ResponseEntity.status(HttpStatus.OK).body(userPrompts);
  }

}
