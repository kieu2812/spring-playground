package com.example.demo;

import com.example.demo.domain.Lesson;
import com.example.demo.repository.LessonRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LessonControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    LessonRepository lessonRepository;

    @Test
    @Transactional
    @Rollback
    public void testGetLessonById() throws Exception {
        Lesson lesson = new Lesson(1, "Spring JPA", "2020-05-20");
        lessonRepository.save(lesson);
        RequestBuilder requestBuilder =  get("/lessons/{id}", 1);

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Spring JPA")))
                .andExpect(jsonPath("$.deliveredOn", is("2020-05-20")));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetLessons() throws Exception {
        Lesson lesson1 = new Lesson(1, "Spring JPA", "2020-05-20");
        Lesson lesson2 = new Lesson(2, "Springboot", "2020-06-25");
        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);
        RequestBuilder requestBuilder =  get("/lessons");

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Spring JPA")))
                .andExpect(jsonPath("$[0].deliveredOn", is("2020-05-20")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Springboot")))
                .andExpect(jsonPath("$[1].deliveredOn", is("2020-06-25")));
    }
    @Test
    @Transactional
    @Rollback
    public void testUpdatePartOfLessonById() throws Exception {
        Lesson lesson1 = new Lesson(1, "Spring JPA", "2020-05-20");

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("New Spring JPA");
        ObjectMapper mapper = new ObjectMapper();
        lessonRepository.save(lesson1);
        RequestBuilder requestBuilder =  patch("/lessons/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lesson2));

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("New Spring JPA")))
                .andExpect(jsonPath("$.deliveredOn", is("2020-05-20")));
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateLessonById() throws Exception {
        Lesson lesson1 = new Lesson(1, "Spring JPA", "2020-05-20");

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("New Spring JPA");
        ObjectMapper mapper = new ObjectMapper();
        lessonRepository.save(lesson1);
        RequestBuilder requestBuilder =  put("/lessons/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lesson2));

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(lesson2.getTitle())))
                .andExpect(jsonPath("$.deliveredOn", is(nullValue())));
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteLessonById() throws Exception {
        Lesson lesson1 = new Lesson(1, "Spring JPA", "2020-05-20");
        lessonRepository.save(lesson1);
        RequestBuilder requestBuilder =  delete("/lessons/{id}", 1);
        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Deleted lesson id %d from db", 1)));
    }
}
