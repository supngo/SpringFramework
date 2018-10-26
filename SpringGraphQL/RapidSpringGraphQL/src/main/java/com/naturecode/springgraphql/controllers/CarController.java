package com.naturecode.springgraphql.controllers;

import com.naturecode.springgraphql.services.CarService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class CarController {

  private final GraphQL graphQL;

  public CarController(CarService carService) {
    GraphQLSchema schema = new GraphQLSchemaGenerator()
      .withResolverBuilders(new AnnotatedResolverBuilder())
      .withOperationsFromSingleton(carService, CarService.class)
      .withValueMapperFactory(new JacksonValueMapperFactory())
      .generate();
    graphQL = GraphQL.newGraphQL(schema).build();
  }

  @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public Map<String, Object> graphql(@RequestBody Map<String, String> request, HttpServletRequest raw) {
    ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput()
      .query(request.get("query"))
      .operationName(request.get("operationName"))
      .context(raw)
      .build());
    return executionResult.toSpecification();
  }
}
