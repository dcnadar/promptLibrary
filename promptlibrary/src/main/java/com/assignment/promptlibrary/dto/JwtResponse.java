package com.assignment.promptlibrary.dto;

public record JwtResponse(
                String accessToken,
                UserDTO user) {

}
