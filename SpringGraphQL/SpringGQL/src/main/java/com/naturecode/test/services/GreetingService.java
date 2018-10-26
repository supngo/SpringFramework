package com.naturecode.test.services;

import java.util.List;
import java.util.Optional;

import com.naturecode.test.models.Greeting;
import com.naturecode.test.repository.GreetingRepo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GreetingService {
  private final GreetingRepo greetingRepo;

  public GreetingService(GreetingRepo greetingRepo) {
    this.greetingRepo = greetingRepo;
  }

  public List<Greeting> getGreetings() {
    return greetingRepo.findAll();
  }

  public Optional<Greeting> getGreetingById(Long id) {
    return greetingRepo.findById(id);
  }

  public Greeting saveGreeting(Greeting greeting) {
    return greetingRepo.save(greeting);
  }

  public void deleteGreeting(Long id) {
    greetingRepo.deleteById(id);
  }
}