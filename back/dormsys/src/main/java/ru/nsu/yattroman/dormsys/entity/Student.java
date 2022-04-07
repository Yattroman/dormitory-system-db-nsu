package ru.nsu.yattroman.dormsys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @Column(name = "faculty")
    private String faculty;
    @Column(name = "education_base")
    private String educationBase;

}
