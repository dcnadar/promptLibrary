package com.assignment.promptlibrary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.promptlibrary.dto.PromptDTO;
import com.assignment.promptlibrary.model.Prompt;
import com.assignment.promptlibrary.s3.S3Service;
import com.assignment.promptlibrary.service.PromptService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/prompts")
  public ResponseEntity<Prompt> createPrompt(@RequestBody PromptDTO promptDTO) {

    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }

  @GetMapping("/prompts")
  public ResponseEntity<List<PromptDTO>> getPublicPrompts() {
    List<PromptDTO> list = promptService.allPublicPrompts();
    return ResponseEntity.status(HttpStatus.OK).body(list);
  }

  @GetMapping("/prompts/{promptId}")
  public ResponseEntity<PromptDTO> getMethodName(@PathVariable String promptId) {
    PromptDTO promptDTO = promptService.getPrompt(promptId);
    return ResponseEntity.status(HttpStatus.OK).body(promptDTO);
  }

  @PutMapping("prompts/{promptId}")
  public ResponseEntity<?> updatePrompt(@PathVariable String promptId, @RequestBody PromptDTO promptDTO) {
    promptService.updatePrompt(promptId, promptDTO);
    return ResponseEntity.status(HttpStatus.OK).body(promptDTO);
  }

  @DeleteMapping("/prompts/{promptId}")
  public ResponseEntity<?> deletePrompt(@PathVariable String promptId) {
    promptService.deletePrompt(promptId);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @GetMapping("/users/me/prompts")
  public ResponseEntity<List<PromptDTO>> getUserPrompts() {
    List<PromptDTO> userPrompts = promptService.getUserPrompts();
    return ResponseEntity.status(HttpStatus.OK).body(userPrompts);
  }

}
