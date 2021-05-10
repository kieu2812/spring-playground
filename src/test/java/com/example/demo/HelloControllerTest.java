package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import  org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    public void testHello() throws Exception {
        RequestBuilder request  = MockMvcRequestBuilders.get("/hello");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));

    }




}
