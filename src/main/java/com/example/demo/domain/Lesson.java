package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="America/New_York")
    private Date deliveredOn;
    public Lesson(int id, String title){
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", deliveredOn='" + deliveredOn + '\'' +
                '}';
    }
}
