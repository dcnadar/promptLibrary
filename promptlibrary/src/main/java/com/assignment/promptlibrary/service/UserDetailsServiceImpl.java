package com.assignment.promptlibrary.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.assignment.promptlibrary.dao.UserDao;
import com.assignment.promptlibrary.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserDao userDao;

  UserDetailsServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDao.findUserByUsername(username);
    if (user != null) {
      UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
          .username(user.getUsername())
          .password(user.getPassword())
          .authorities(user.getRole())
          .build();
      return userDetails;
    }
    throw new UsernameNotFoundException("User not found with username" + username);
  }

}
