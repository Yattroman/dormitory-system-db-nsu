package ru.nsu.yattroman.dormsys.entity;

import jakarta.persistence.*;

@Entity
public class Furniture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "inner_number")
    private int innerNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "count")
    private int count;

}
