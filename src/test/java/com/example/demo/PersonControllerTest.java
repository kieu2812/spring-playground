package com.example.demo;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    PersonRepository personRepository;

    @Test
    @Transactional
    @Rollback
    public void testGetPerson() throws Exception {
        Person person = Person.builder().firstName("Kieu").lastName("Nguyen").build();

        personRepository.save(person);
        RequestBuilder requestBuilder = get("/people");

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].id", equalTo(person.getId())))
                .andExpect(jsonPath("$[0].firstName", is("Kieu")))
                .andExpect(jsonPath("$[0].lastName", is("Nguyen")));

    }
    @Test
    @Transactional
    @Rollback
    public void testCreatePerson() throws JsonProcessingException, Exception {
        Person person = new Person();
        person.setFirstName("Kieu");
        person.setLastName("Nguyen");
        ObjectMapper mapper =  new ObjectMapper();
        String json =  mapper.writeValueAsString(person);
        RequestBuilder requestBuilder =  post("/people")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.firstName", is("Kieu")))
                .andExpect(jsonPath("$.lastName", is("Nguyen")));
    }
}
