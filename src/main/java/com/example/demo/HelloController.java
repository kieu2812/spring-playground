package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RestController
public class HelloController {

//    @Autowired
//    private MathService mathService;

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
       // return mathService.operateNumbers(operation, x, y);
        return operateNumbers(operation, x, y);
    }

    @GetMapping("/math/sum")
    public String sumListOfNumbers(@RequestParam("n") List<Integer> numbers){
       // return mathService.sumNumbers(numbers);
        return sumNumbers(numbers);

    }


    @PostMapping("/math/volume/{length}/{width}/{height}")
    public String calculateVolumeOfShape(@PathVariable("length") int length, @PathVariable("width") int width, @PathVariable("height") int height ){
       // return mathService.calculateVolumn(length, width, height);
        return calculateVolume(length, width, height);

    }

    @PostMapping("/math/area")
    public String calculateAreaOfShape(@RequestParam(value = "type", required = true) String type, @RequestParam(value = "radius", required = false) Integer radius,
                                @RequestParam(value = "width", required = false) Integer width, @RequestParam(value = "height", required = false) Integer height){
        // return mathService.calculateVolumn(length, width, height);
        String result=null;
        if("rectangle".equalsIgnoreCase(type)){
            result = calculateRectangleArea( width, height);
        }
        else if("circle".equalsIgnoreCase(type)){
            result = calculateCircleArea( radius);
        }
        else
            result ="Invalid";
        return result;

    }

    @GetMapping("/cookies")
    public String getCookies(@CookieValue(name= "foo") String cookie){
        return cookie;
    }

    @GetMapping("/header")
    public String getHeader(@RequestHeader(name="Host") String host){
        return host;
    }
    private String calculateRectangleArea(int width, int height) {
        return String.format("Area of %dx%d rectangle is %d", width, height, width*height);
    }
    private String calculateCircleArea(int radius) {
        float PI= 3.141592653589793f;
        return String.format("Area of a circle with radius of %d is %f", radius, radius* PI);
    }

    public String operateNumbers(String operation, int x, int y){

        String result=null;
        switch (operation){
            case "multiply":
                result= x +" * " + y + " = " + (x*y);
                break;
            case "divide":
                result= x +" / " + y + " = " + (x/y);
                break;
            case "subtract":
                result= x +" - " + y + " = " + (x-y);
                break;
            default:
                result= x +" + " + y + " = " + (x+y);
                break;
        }
        return result;

    }
    public String sumNumbers(List<Integer> numbers){
        StringBuilder builder = new StringBuilder();
        long sum = numbers.stream().mapToInt( e-> {
            builder.append(" + " + e);
            return Integer.valueOf(e);
        }).sum();
        builder.append(" = " + sum);
        return builder.toString().isEmpty() ? "" : builder.toString().substring(3);

    }

    public String calculateVolume(int length, int width, int height) {
        int volume = length * width * height;
        return String.format("The volume of a %dx%dx%d rectangle is %d", length, width, height, volume);
    }


}
