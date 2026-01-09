package com.cwa.crudspringboot.repository;


import com.cwa.crudspringboot.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void shouldGetAllPerson(){
        //Arrange //Given
        // we have already a database test

        //Act //When
        List<Person> persons = personRepository.findAll();

        //Assert //Then
        assertEquals(2,persons.size());
        assertEquals("John DOe" , persons.get(0).getName());
        assertEquals("Fianarantsoa", persons.get(1).getCity());
    }

    @Test
    void shoulgetPersonById(){
       Person person = personRepository.findById(1L).get();

       assertEquals("John DOe", person.getName());
    }

    @Test
    void shouldSavedPerson(){
        Person person = new Person();
        person.setName("Jean");
        person.setCity("Sakaraha");
        person.setPhoneNumber("54455");

       Person newPerson =  personRepository.save(person);

        assertNotNull(newPerson.getId());
    }

    @Test
    void shouldUpdatePerson(){
        Person person = personRepository.findById(1L).get();

        person.setName("William");

        Person newPerson = personRepository.save(person);

        //Assert
        assertEquals("William" , newPerson.getName());
    }

    @Test
    void shouldDeletePerson(){
        personRepository.deleteById(1L);

       Optional <Person> person = personRepository.findById(1L);

       assertFalse(person.isPresent());
    }
}