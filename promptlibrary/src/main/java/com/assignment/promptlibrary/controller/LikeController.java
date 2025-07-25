package com.assignment.promptlibrary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.promptlibrary.service.LikeService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api")
public class LikeController {

  private final LikeService likeService;

  public LikeController(LikeService likeService) {
    this.likeService = likeService;
  }

  @PostMapping("/prompts/{promotId}/like")
  public ResponseEntity<String> likePrompt(@PathVariable String promptId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    String result = likeService.likePrompt(promptId, username);
    return ResponseEntity.ok(result);

  }

  @DeleteMapping("/prompts/{promptId}/like")
  public ResponseEntity<String> unlikePrompt(@PathVariable String promptId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    String result = likeService.unlikePrompt(promptId, username);
    return ResponseEntity.ok(result);
  }

}
