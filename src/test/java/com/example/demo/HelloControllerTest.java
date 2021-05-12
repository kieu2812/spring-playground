package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private MathService mathService;

    @Test
    public void testHello() throws Exception {
        RequestBuilder request  = get("/hello");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));

    }

    @Test
    public void testGreetingByParams() throws Exception {
        String first="kieu", last="nguyen";
        RequestBuilder request  = get("/greeting/{first}/{last}", first , last);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Greeting by params: " +first + last));

    }

    @Test
    public void testGreetingByQueries() throws Exception {
        String first="kieu", last="nguyen";
        RequestBuilder request  = get("/greeting?first={first}&last={last}", first, last);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Greeting by queries: " +first + last));

    }
    @Test
    public void testGreetingByFormData() throws Exception {
        String first="kieu", last="nguyen";
        RequestBuilder request  = post("/greeting")
                .param("first", first)
                .param("last", last);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Greeting by form data: " +first + last));

    }

    @Test
    public void testGetPI() throws Exception{
        RequestBuilder request = get("/math/pi");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("3.141592653589793"));
    }

    @Test
    public void testAddOperations() throws  Exception{
        int x= 5, y=7;
        RequestBuilder request = get("/math/calculate")
                .param("x", String.valueOf(x))
                .param("y",  String.valueOf(y))
                .param("operation", "add");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(x +" + " + y + " = " + (x+y)));
    }
//    @Test
//    public void testAddDefault() throws  Exception{
//        int x= 5, y=7;
//        RequestBuilder request = get("/math/calculate")
//                .param("x", String.valueOf(x))
//                .param("y",  String.valueOf(y));
//        this.mvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().string(x +" + " + y + " = " + (x+y)));
//    }
//    @Test
//    public void testSubtractOperations() throws  Exception{
//        int x= 5, y=7;
//
//        RequestBuilder request = get("/math/calculate")
//                .param("x", String.valueOf(x))
//                .param("y",  String.valueOf(y))
//                .param("operation", "subtract");
//        this.mvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().string(x +" - " + y + " = " + (x-y)));
//    }
//
//    @Test
//    public void testMultiplyOperations() throws  Exception{
//        int x= 5, y=7;
//        RequestBuilder request = get("/math/calculate")
//                .param("x", String.valueOf(x))
//                .param("y",  String.valueOf(y))
//                .param("operation", "multiply");
//        this.mvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().string(x +" * " + y + " = " + (x*y)));
//    }
//    @Test
//    public void testDivideOperations() throws  Exception{
//        int x= 5, y=7;
//        RequestBuilder request = get("/math/calculate")
//                .param("x", String.valueOf(x))
//                .param("y",  String.valueOf(y))
//                .param("operation", "divide");
//        this.mvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().string(x +" / " + y + " = " + (x/y)));
//    }
//    @Test
//    public void testSumNumbers() throws Exception{
//        int x=5, y=7, z=9;
//        RequestBuilder request = get("/math/sum")
//                .param("n", ""+ x)
//                .param("n", ""+ y)
//                .param("n", ""+ z);
//        this.mvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().string("5 + 7 + 9 = 21"));
//    }
        @Test
    public void testVolumn() throws Exception{
        int length=5, width=7, height=9;
        int volumn =  length * width * height;
        RequestBuilder request = get("/math/volumn")
                .param("length", ""+ length)
                .param("width", ""+ width)
                .param("height", ""+ height);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("The volume of a %dx%dx%d rectangle is %d", length, width, height, volumn)));
    }

}
