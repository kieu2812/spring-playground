package com.example.demo.controller;

import com.example.demo.domain.Lesson;
import com.example.demo.repository.LessonRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @GetMapping("")
    public Iterable<Lesson> getLessons(){

        return this.lessonRepository.findAll();

    }


    @DeleteMapping("/{id}")
    public String deleteLesson(@PathVariable int id){
        this.lessonRepository.deleteById(id);
        return String.format("Deleted lesson id %d from db", id);
    }
    @PostMapping("")
    public Lesson createLesson(@RequestBody Lesson lesson){

        return this.lessonRepository.save(lesson);

    }
    @PutMapping("/{id}")
    public Lesson updateLesson(@RequestBody Lesson lesson, @PathVariable int id ){
        if(this.lessonRepository.findById(id).isPresent()) {

            lesson.setId(id);
            lesson = this.lessonRepository.save(lesson);
        }

        return lesson;
    }

    @PatchMapping("/{id}")
    public Lesson updatePartOfLesson(@RequestBody Lesson lesson, @PathVariable int id ){
       Lesson lessonInDb = this.lessonRepository.findById(id).get();
        if(lessonInDb!=null) {
            lesson.setId(id);
            if(lesson.getDeliveredOn()!=null){
                lessonInDb.setDeliveredOn(lesson.getDeliveredOn());
            }
            if(lesson.getTitle()!=null){
                lessonInDb.setTitle(lesson.getTitle());
            }
        }
        return this.lessonRepository.save(lessonInDb);
    }
    @GetMapping("/find/{title}")
    public List<Lesson> findLessonByTitle(@PathVariable String title){
        System.out.println("Title: " + title);
        return this.lessonRepository.findByTitle(title.toLowerCase());
    }

    @GetMapping("/findAfter/{deliveredOn}")
    public List<Lesson> findLessonByDeliveredOnAfter(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date deliveredOn){
        System.out.println(deliveredOn);
        return this.lessonRepository.findByDeliveredOnAfter(deliveredOn);
    }

    @GetMapping("/between")
    public List<Lesson> findLessonByDeliveredOnAfter(@RequestParam("date1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                                                     @RequestParam("date2") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2){

        return this.lessonRepository.findByDeliveredOnBetween(date1, date2);
    }
}
