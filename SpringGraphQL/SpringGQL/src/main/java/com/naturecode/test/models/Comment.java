package com.naturecode.test.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Comment {
  @Id
  @GeneratedValue
  @Column(name="comment_id")
  private Long id;

  @Column(name="comment_content")
  private String content;

  @ManyToOne(fetch = FetchType.EAGER)
  // @JoinColumn(name = "greeting_id", nullable = false)
  @JoinColumn(name = "greeting_id")
  private Greeting greeting;
}