package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String getGreeting(){
        return "hello";
    }
    @GetMapping("/greeting/{first}/{last}")
    public String greetingByParams(@PathVariable String first, @PathVariable String last){

        return "Greeting by params: " +first + last;
    }
    @GetMapping("/greeting")
    public String greetingByQuery(@RequestParam String first, @RequestParam String last){
        return "Greeting by queries: " +first + last;
    }
    @PostMapping("/greeting")
    public String greetingByFormData(@RequestParam String first, @RequestParam String last){
        return "Greeting by form data: " +first + last;
    }

    @GetMapping("/math/pi")
    public String getPI(){
        return "3.141592653589793";
    }
}
