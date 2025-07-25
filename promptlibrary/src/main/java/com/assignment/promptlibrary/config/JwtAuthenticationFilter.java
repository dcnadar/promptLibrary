package com.assignment.promptlibrary.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.assignment.promptlibrary.service.JwtService;
import com.assignment.promptlibrary.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private JwtService jwtService;
  private UserDetailsServiceImpl userDetailsServiceImpl;

  public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
    this.jwtService = jwtService;
    this.userDetailsServiceImpl = userDetailsServiceImpl;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);

      if (jwtService.validateToken(token)) {
        String usernameFromToken = jwtService.getUsernameFromToken(token);
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(usernameFromToken);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
              null,
              userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);
          ;
        }
      }
    }
    filterChain.doFilter(request, response);
    throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
  }

}
