package com.naturecode.test.services;

import java.util.List;
import java.util.Optional;

import com.naturecode.test.models.Comment;
import com.naturecode.test.repository.CommentRepo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
  private final CommentRepo commentRepo;

  public CommentService(CommentRepo commentRepo) {
    this.commentRepo = commentRepo;
  }
  
  public List<Comment> getComments() {
    return commentRepo.findAll();
  }

  public Optional<Comment> getCommentById(Long id) {
    Optional<Comment> comment =  commentRepo.findById(id);
    return comment;
  }

  public Comment saveComment(Comment comment) {
    return commentRepo.save(comment);
  }

  public void deleteComment(Long id) {
    commentRepo.deleteById(id);
  }
}