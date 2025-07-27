package com.assignment.promptlibrary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.assignment.promptlibrary.service.LikeService;
import com.assignment.promptlibrary.utils.AuthUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @PreAuthorize("hasAuthority('BUYER')")
  @PostMapping("/prompts/{promptId}/like")
  public ResponseEntity<String> likePrompt(@PathVariable String promptId) {
    String username = AuthUtils.getCurrentUsername();
    String result = likeService.likePrompt(promptId, username);
    return ResponseEntity.ok(result);

  }

  @PreAuthorize("hasAuthority('BUYER')")
  @DeleteMapping("/prompts/{promptId}/like")
  public ResponseEntity<String> unlikePrompt(@PathVariable String promptId) {
    String username = AuthUtils.getCurrentUsername();
    String result = likeService.unlikePrompt(promptId, username);
    return ResponseEntity.ok(result);
  }

}
