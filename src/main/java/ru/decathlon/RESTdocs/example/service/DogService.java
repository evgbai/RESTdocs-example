package ru.decathlon.RESTdocs.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.decathlon.RESTdocs.example.model.Dog;
import ru.decathlon.RESTdocs.example.repository.DogRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DogService {

    private final DogRepository repository;

    @Autowired
    public DogService(DogRepository repository) {
        this.repository = repository;
    }

    public List<Dog> getDogs() {
        return repository.findAll();
    }

    public Dog getOneDogById(Long dogId) {
        return repository.findById(dogId)
                .orElseThrow(NoSuchElementException::new);
    }

    public void saveDog(Dog dog) {
        repository.save(dog);
    }

    public void deleteDogById(Long dogId) {
        var dog = repository.findById(dogId)
                .orElseThrow(NoSuchElementException::new);
        repository.delete(dog);
    }
}
