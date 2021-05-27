package com.example.demo.controller;

import com.example.demo.domain.Lesson;
import com.example.demo.repository.LessonRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    private LessonRepository lessonRepository;

    public LessonController(LessonRepository lessonRepository){
        this.lessonRepository= lessonRepository;
    }
    @GetMapping("/{id}")
    public Lesson getLesson(@PathVariable int id){
        return this.lessonRepository.findById(id).orElse(new Lesson() );
    }

    @GetMapping("/")
    public Iterable<Lesson> getLessons(){

        return this.lessonRepository.findAll();

    }


    @DeleteMapping("/{id}")
    public String deleteLesson(@PathVariable int id){
        this.lessonRepository.deleteById(id);
        return String.format("Deleted lesson id %d from db", id);
    }
    @PostMapping("/")
    public Lesson createLesson(@RequestBody Lesson lesson){

        return this.lessonRepository.save(lesson);

    }

}
