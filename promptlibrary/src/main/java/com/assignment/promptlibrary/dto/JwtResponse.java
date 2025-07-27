package com.assignment.promptlibrary.dto;

public record JwtResponse(
        String accessToken,
        // String refreshToken,
        UserDTO user) {

}
