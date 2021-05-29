package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private PersonRepository personRepository;
    public PersonController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
    @GetMapping("/people")
    public Iterable<Person> getPeople(){
        return this.personRepository.findAll();
    }

    @PostMapping("/people")
    public Person createPerson(@RequestBody Person person){

        return this.personRepository.save(person);
    }

}
