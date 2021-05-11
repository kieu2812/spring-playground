package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    @Test
    public void testGreetingByParams() throws Exception {
        String first="kieu", last="nguyen";
        RequestBuilder request  = MockMvcRequestBuilders.get("/greeting/"+ first +"/"+ last);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Greeting by params: " +first + last));

    }

    @Test
    public void testGreetingByQueries() throws Exception {
        String first="kieu", last="nguyen";
        RequestBuilder request  = MockMvcRequestBuilders.get("/greeting?first="+ first +"&last="+ last);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Greeting by queries: " +first + last));

    }
    @Test
    public void testGreetingByFormData() throws Exception {
        String first="kieu", last="nguyen";
        RequestBuilder request  = MockMvcRequestBuilders.post("/greeting")
                .param("first", first)
                .param("last", last);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Greeting by form data: " +first + last));

    }

    @Test
    public void testGetPI() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/math/pi");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("3.141592653589793"));
    }
}
