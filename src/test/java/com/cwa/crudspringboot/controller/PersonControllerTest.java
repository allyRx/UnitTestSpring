package com.cwa.crudspringboot.controller;


import com.cwa.crudspringboot.entity.Person;
import com.cwa.crudspringboot.service.PersonService;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void shouldGetAllPerson() throws Exception{

        //Arrange
        Person p1 = new Person(1L,"Jhon Wick", "Los angeles","45785411");
        Person p2 = new Person(2L,"Jhon Peters", "mexico","45785417881");

        when(personService.findAll()).thenReturn(List.of(p1,p2));

        //Act and //Assert
        mockMvc.perform(get("/api/persons")).andExpect(status().isOk());
    }

    @Test
    void shouldReturnPersonById() throws Exception{
        Person p1 = new Person(1L,"Jhon Wick", "Los Angeles","45785411");

        when(personService.findById(1L)).thenReturn(p1);

        mockMvc.perform(get("/api/persons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jhon Wick"))
                .andExpect(jsonPath("$.city").value("Los Angeles"));

    }


    @Test
    void shouldReturnCreatedPerson() throws Exception {
        String json = """
                {
                "id": 1,
                "name": "John Doe",
                "city": "New York",
                "phoneNumber": "103-486-7890"
                }
                """;
        Person p = new Person(1L, "John Doe", "New York", "103-486-7890");
        when(personService.saveOrUpdate(p)).thenReturn(p);

        mockMvc.perform(post("/api/persons").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldUpdatePerson() throws Exception{
        String json = """
                {
                "id": 1,
                "name": "John Doe",
                "city": "Los Angeles",
                "phoneNumber": "103-486-7890"
                }
                """;
        Person Existingperson = new Person(1L,"John Doe","New York","103-486-7890");
        Person NewPerson = new Person(1L,"John Doe","Los Angeles","103-486-7890");

        when(personService.findById(1L)).thenReturn(Existingperson);
        when(personService.saveOrUpdate(NewPerson)).thenReturn(NewPerson);

        mockMvc.perform(put("/api/persons/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Los Angeles"));
    }

    @Test
    void shouldDeletePerson() throws  Exception {
        Person Existingperson = new Person(1L,"John Doe","New York","103-486-7890");

        when(personService.findById(1L)).thenReturn(Existingperson);

        mockMvc.perform(delete("/api/persons/1"))
                .andExpect(status().isOk());
    }
}