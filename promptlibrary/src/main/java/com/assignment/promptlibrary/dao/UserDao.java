package com.assignment.promptlibrary.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.assignment.promptlibrary.model.User;

@Component
public class UserDao {
  @Autowired
  private MongoTemplate mongoTemplate;

  public void registerUser(User userEntity) {
    mongoTemplate.save(userEntity);
  }

  public Optional<User> findUserByEmail(String email) {
    Query query = new Query();
    query.addCriteria(Criteria.where("email").is(email));
    User entityUser = mongoTemplate.findOne(query, User.class);
    return Optional.ofNullable(entityUser);
  }

  public User findUserByUsername(String username) {
    Query query = new Query();
    query.addCriteria(Criteria.where("username").is(username));
    User entityUser = mongoTemplate.findOne(query, User.class);
    return entityUser;
  }

  public Optional<User> findUserById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(id));
    User entityUser = mongoTemplate.findOne(query, User.class);
    return Optional.ofNullable(entityUser);
  }

  public boolean updateUser(User user, String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(id));
    Update update = new Update();
    update.set("username", user.getUsername());
    update.set("email", user.getEmail());
    update.set("password", user.getPassword());
    update.set("role", user.getRole());
    User updatedUser = mongoTemplate.findAndModify(query, update, User.class);
    if (updatedUser != null) {
      return true;
    }
    return false;
  }
}
