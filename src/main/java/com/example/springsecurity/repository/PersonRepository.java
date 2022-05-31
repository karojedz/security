package com.example.springsecurity.repository;

import com.example.springsecurity.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
