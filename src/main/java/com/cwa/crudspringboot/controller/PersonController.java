package com.cwa.crudspringboot.controller;

import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    @Operation(
            summary = "Recuperer tous les personnes ",
            description = "Retournes tous les personnes dans la base des donnes"

    )
    @ApiResponse(responseCode = "200", description = "Personnes trouve avec succes ")
    @ApiResponse(responseCode = "404", description = "Personne pas trouver")
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Ajoutez une personne",
            description = "Pour ajouter une personnes ave son name , city , phoneNumber"
    )
    @ApiResponse(responseCode = "201", description = "Personne bien cree")
    @ApiResponse(responseCode = "500", description = "Internal server Error")
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person personCreated = personService.saveOrUpdate(person);
        return new ResponseEntity<>(personCreated, HttpStatus.CREATED);
    }


    @Operation(
            summary = "recuperer une Personne par son ID"
    )
    @ApiResponse(responseCode = "200", description = "Person recuperer par son ID avec succes")
    @ApiResponse(responseCode = "404", description = "Person pas trouver ")
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personService.findById(id);

        return Objects.nonNull(person) ? new ResponseEntity<>(person, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Mise a jour d'une personne"
    )
    @ApiResponse(responseCode = "201", description = "Person bien mise a jour ")
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        Person existingPerson = personService.findById(id);

        if (Objects.nonNull(existingPerson)) {
            existingPerson.setCity(personDetails.getCity());
            existingPerson.setPhoneNumber(personDetails.getPhoneNumber());

            Person updatedPerson = personService.saveOrUpdate(existingPerson);
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "SUppression Person")
    @ApiResponse(responseCode = "200", description = "Personne bien supprimer!!")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        Person person = personService.findById(id);

        if (Objects.nonNull(person)) {
            personService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
