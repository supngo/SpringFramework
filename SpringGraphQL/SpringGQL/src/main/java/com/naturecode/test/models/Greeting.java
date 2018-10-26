package com.naturecode.test.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Greeting {
  @Id
  @GeneratedValue
  @Column(name="greeting_id")
  private Long id;

  @Column(name="greeting_message")
  private String message;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "greeting", orphanRemoval = true)
  private List<Comment> comments;

  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setGreeting(this);
  }

  public void removeComment(Comment comment) {
    comments.remove(comment);
    comment.setGreeting(null);
  }
}