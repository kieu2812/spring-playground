package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RestController
public class HelloController {

    @Autowired
    private MathService mathService;

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
    @GetMapping("/math/calculate")
    public String calculateNumbers(@RequestParam(value="operation", required = false, defaultValue = "add") String operation,
                                   @RequestParam("x") int x, @RequestParam("y") int y){
        return mathService.operateNumbers(operation, x, y);
    }

    @GetMapping("/math/sum")
    public String sumNumbers(@RequestParam("n") List<Integer> numbers){
        return mathService.sumNumbers(numbers);

    }


    @GetMapping("/math/volume/{length}/{width}/{height}")
    public String sumNumbers(@PathVariable("length") int length, @PathVariable("width") int width, @PathVariable("height") int height ){
        return mathService.calculateVolumn(length, width, height);

    }
}
