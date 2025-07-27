package com.assignment.promptlibrary.service;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.access.expiration}")
  private long ACCESS_TOKEN_EXPIRATION;

  @Value("${jwt.refresh.expiration}")
  private long REFRESH_TOKEN_EXPIRATION;

  private SecretKey SECRET_KEY;

  @PostConstruct
  public void init() {
    byte[] keyBytes = Base64.getDecoder().decode(secret);
    this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateToken(String username) {
    // long expiration = isAccessToken ? ACCESS_TOKEN_EXPIRATION :
    // REFRESH_TOKEN_EXPIRATION;
    long expiration = ACCESS_TOKEN_EXPIRATION;
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
        .compact();

  }

  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(SECRET_KEY)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}
