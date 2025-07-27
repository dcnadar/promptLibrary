package com.assignment.promptlibrary.service.serviceInterfaces;

public interface ILikeService {
  String likePrompt(String promptId, String username);

  String unlikePrompt(String promptId, String username);
}
