package ru.decathlon.RESTdocs.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.decathlon.RESTdocs.example.model.Dog;
import ru.decathlon.RESTdocs.example.service.DogService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DogController {

    private final DogService service;

    @Autowired
    public DogController(DogService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Dog>> getDogs() {
        var dogs = service.getDogs();
        return ResponseEntity.ok().body(dogs);
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<Dog> getOneDogById(@PathVariable Long dogId) {
        var dog = service.getOneDogById(dogId);
        return ResponseEntity.ok().body(dog);
    }

    @PostMapping
    public ResponseEntity<Void> saveDog(@RequestBody @Valid Dog dog) {
        service.saveDog(dog);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{dogId}")
    public ResponseEntity<Void> deleteDogById(@PathVariable Long dogId) {
        service.deleteDogById(dogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
