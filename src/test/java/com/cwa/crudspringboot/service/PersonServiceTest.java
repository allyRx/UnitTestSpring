package com.cwa.crudspringboot.service;

import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void shouldFindAllPerson() {
        //Arrange or Given
        Person p1 = new Person(1L,"Jhon Wick", "Los angeles","45785411");
        Person p2 = new Person(2L,"Jhon Peters", "mexico","45785417881");

        when(personRepository.findAll()).thenReturn(List.of(p1,p2));

        //Act or When
        List<Person> persons = personService.findAll();

        //Assert or Then
        assertThat(persons).hasSize(2);
        assertThat(persons.get(0).getName()).isEqualTo("Jhon Wick");

    }

    @Test
    void shouldFindPersonById(){
        //Arrange
        Person p1 = new Person(1L,"Jhon Wick", "Los angeles","45785411");

        when(personRepository.findById(1L)).thenReturn(Optional.of(p1));

        //Act
        Person person = personService.findById(1L);


        //Assert
        assertThat(person).isEqualTo(p1);
    }

    @Test
    void shouldSavedPerson(){

        //Given
        Person person = new Person();
        person.setName("Jack");
        person.setPhoneNumber("454545");
        person.setCity("Africa");

        when(personRepository.save(person)).thenReturn(person);

        //When
        Person newPerson = personService.saveOrUpdate(person);

        //Then
        assertNotNull(newPerson);
        assertThat(newPerson.getName()).isEqualTo("Jack");
        assertThat(newPerson.getCity()).isEqualTo("Africa");
    }

    @Test
    void shouldDeletePerson(){
        //Arrange
        Person p1 = new Person(1L,"Jhon Wick", "Los angeles","45785411");

        when(personRepository.findById(1L)).thenReturn(Optional.of(p1));

        //When
        personService.deleteById(1L);

        Optional<Person> person = personRepository.findById(1L);

        //Then
        verify(personRepository).deleteById(1L);
    }
}