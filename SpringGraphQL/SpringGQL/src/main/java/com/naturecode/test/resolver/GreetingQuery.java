package com.naturecode.test.resolver;

import java.util.List;
import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.naturecode.test.models.Comment;
import com.naturecode.test.models.Greeting;
import com.naturecode.test.services.CommentService;
import com.naturecode.test.services.GreetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GreetingQuery implements GraphQLQueryResolver {

  @Autowired
  private GreetingService greetingService;

  @Autowired
  private CommentService commentService;

  public List<Greeting> getAllGreetings() {
    return greetingService.getGreetings();
  }

  public Optional<Greeting> getGreeting(Long id) {
    return greetingService.getGreetingById(id);
  }

  public List<Comment> getAllComments() {
    return commentService.getComments();
  }

  public Optional<Comment> getComment(Long id) {
    return commentService.getCommentById(id);
  }
}