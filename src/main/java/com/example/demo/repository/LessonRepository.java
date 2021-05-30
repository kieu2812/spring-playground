package com.example.demo.repository;

import com.example.demo.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("select l from Lesson l where lower(l.title) like %:title%")
    List<Lesson> findByTitle(@Param("title")String title);

    List<Lesson> findByDeliveredOnAfter(Date deliveredOn);

    @Query("select l from Lesson l where deliveredOn between :date1 and :date2")
    List<Lesson> findByDeliveredOnBetween(@Param("date1") Date date1, @Param("date2") Date date2);
}
