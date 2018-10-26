package com.naturecode.springgraphql.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class Car {
  @Id @GeneratedValue
  @GraphQLQuery(name = "id", description = "A car's id")
  private Long id;

  @GraphQLQuery(name = "name", description = "A cßr's name")
  private @NonNull String name;
}