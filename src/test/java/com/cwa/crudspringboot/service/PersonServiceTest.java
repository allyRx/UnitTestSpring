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
}