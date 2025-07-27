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

  public User registerUser(User userEntity) {
    return mongoTemplate.insert(userEntity);
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

  public Optional<User> updateUser(User user, String id) {
    // Query query = new Query();
    // query.addCriteria(Criteria.where("id").is(id));
    // Update update = new Update();
    // if (user.getUsername() != null) {
    // update.set("username", user.getUsername());
    // }
    // if (user.getPassword() != null) {
    // update.set("password", user.getPassword());
    // }
    // // update.set("email", user.getEmail());
    // // update.set("role", user.getRole());
    // User updatedUser = mongoTemplate.findAndModify(query, update, User.class);

    user.setId(id); // Ensure ID is set
    User savedUser = mongoTemplate.save(user, "users");
    return Optional.ofNullable(savedUser);
  }
}
