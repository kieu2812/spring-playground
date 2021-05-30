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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-DD-mm");

        Lesson lesson = new Lesson(1, "Spring JPA", format.parse("2020-05-20"));
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-DD-mm");
        Lesson lesson1 = new Lesson(1, "Spring JPA", format.parse("2020-05-20"));
        Lesson lesson2 = new Lesson(2, "Springboot", format.parse("2020-06-25"));
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-DD-mm");

        Lesson lesson1 = new Lesson(1, "Spring JPA", format.parse("2020-05-20"));

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Lesson lesson1 = new Lesson(1, "Spring JPA", format.parse("2020-05-20"));

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Lesson lesson1 = new Lesson(1, "Spring JPA", format.parse("2020-05-20"));
        lessonRepository.save(lesson1);
        RequestBuilder requestBuilder =  delete("/lessons/{id}", 1);
        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Deleted lesson id %d from db", 1)));
    }

    @Test
    @Transactional
    @Rollback
    public void testFindLessonByTitle() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Lesson lesson1 = new Lesson(1, "Spring JPA", format.parse("2020-01-20"));
        Lesson lesson2 = new Lesson(2, "Spring Boot", format.parse("2020-02-20"));
        Lesson lesson3 = new Lesson(3, "AWS", format.parse("2020-03-20"));

        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);
        lessonRepository.save(lesson3);
        RequestBuilder requestBuilder =  get("/lessons/find/{title}", "spring");
        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is(lesson1.getTitle())))
                .andExpect(jsonPath("$[1].title", is(lesson2.getTitle())))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByDeliveredOnAfter() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Lesson lesson1 = new Lesson(1, "Spring JPA", format.parse("2020-01-20"));
        Lesson lesson2 = new Lesson(2, "Spring Boot", format.parse("2020-02-20"));
        Lesson lesson3 = new Lesson(3, "AWS", format.parse("2020-03-20"));

        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);
        lessonRepository.save(lesson3);
        System.out.println(format.parse("2020-02-10"));
        RequestBuilder requestBuilder =  get("/lessons/findAfter/{deliveredOn}", "2020-02-10");
        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].deliveredOn", is("2020-02-20")))
                .andExpect(jsonPath("$.[0].title", is(lesson2.getTitle())))
                .andExpect(jsonPath("$.[1].deliveredOn", is("2020-03-20")))
                .andExpect(jsonPath("$.[1].title", is(lesson3.getTitle())));
    }
    @Test
    @Transactional
    @Rollback
    public void testFindByDeliveredOnBetween() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Lesson lesson1 = new Lesson(1, "Spring JPA", format.parse("2020-01-20"));
        Lesson lesson2 = new Lesson(2, "Spring Boot", format.parse("2020-02-20"));
        Lesson lesson3 = new Lesson(3, "AWS", format.parse("2020-03-20"));

        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);
        lessonRepository.save(lesson3);
        System.out.println(format.parse("2020-02-10"));
        RequestBuilder requestBuilder =  get("/lessons/between")
                .param("date1", "2020-02-01")
                .param("date2","2020-03-30");
        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].deliveredOn", is("2020-02-20")))
                .andExpect(jsonPath("$.[0].title", is(lesson2.getTitle())))
                .andExpect(jsonPath("$.[1].deliveredOn", is("2020-03-20")))
                .andExpect(jsonPath("$.[1].title", is(lesson3.getTitle())));
    }



}
