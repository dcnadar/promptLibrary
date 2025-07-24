package com.assignment.promptlibrary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.promptlibrary.dao.LikeDao;
import com.assignment.promptlibrary.service.LikeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class LikeController {

  private final LikeService likeService;

  public LikeController(LikeService likeService) {
    this.likeService = likeService;
  }

  @PostMapping("/prompts/{promotId}/like")
  public ResponseEntity<String> likePrompt(@PathVariable String promptId) {
    return ResponseEntity.ok(likeService.likePrompt(promptId));

  }

  @DeleteMapping("/prompts/{promptId}/like")
  public ResponseEntity<String> unlikePrompt(@PathVariable String promptId) {
    return ResponseEntity.ok(likeService.unlikePrompt(promptId));
  }

}
