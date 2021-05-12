package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MathService {
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


    public String calculateVolumn(int length, int width, int height) {
        long volumn = length * width * height;
        return String.format("The volume of a %dx%dx%d rectangle is %d", length, width, height, volumn);
    }
}
