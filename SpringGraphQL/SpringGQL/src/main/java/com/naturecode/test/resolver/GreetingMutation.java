package com.naturecode.test.resolver;

import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.naturecode.test.models.Comment;
import com.naturecode.test.models.Greeting;
import com.naturecode.test.services.CommentService;
import com.naturecode.test.services.GreetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GreetingMutation implements GraphQLMutationResolver {

  @Autowired
  private GreetingService greetingService;
  
  @Autowired
  private CommentService commentService;

  public Greeting newGreeting(String message) {
    Greeting greeting = new Greeting();
    greeting.setMessage(message);
    return greetingService.saveGreeting(greeting);
  }

  public Greeting updateGreeting(Long id, String message) {
    Optional<Greeting> result = greetingService.getGreetingById(id);
    if(result.isPresent()){
      result.get().setMessage(message);
      return greetingService.saveGreeting(result.get());
    }
    return null;
  }

  public Greeting deleteGreeting(Long id) {
    Optional<Greeting> result = greetingService.getGreetingById(id);
    if(result.isPresent()){
      Greeting greeting = new Greeting();
      greeting = result.get();
      greetingService.deleteGreeting(id);
      return greeting;
    }
    return null;
  }

  public Comment newComment(Long id, String content) {
    Optional<Greeting> result = greetingService.getGreetingById(id);
    if(result.isPresent()){
      Comment comment = new Comment();
      comment.setContent(content);
      comment.setGreeting(result.get());
      return commentService.saveComment(comment);
    }
    return null;
  }

  public Comment updateComment(Long id, String content) {
    Optional<Comment> result = commentService.getCommentById(id);
    if(result.isPresent()){
      result.get().setContent(content);
      return commentService.saveComment(result.get());
    }
    return null;
  }

  public Comment deleteComment(Long id) {
    Optional<Comment> comment = commentService.getCommentById(id);
    if(comment.isPresent()){
      Comment keep = comment.get();
      Greeting greeting = comment.get().getGreeting();
      greeting.removeComment(comment.get());
      greetingService.saveGreeting(greeting);
      return keep;
    }
    return null;
  }
}