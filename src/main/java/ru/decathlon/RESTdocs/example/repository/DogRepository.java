package ru.decathlon.RESTdocs.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.decathlon.RESTdocs.example.model.Dog;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
