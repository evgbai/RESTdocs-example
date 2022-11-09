package ru.decathlon.RESTdocs.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dogs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;
    @NotBlank
    @Size(min = 1, max = 30)
    private String breed;

    public Dog(String name, String breed) {
        this.name = name;
        this.breed = breed;
    }
}
