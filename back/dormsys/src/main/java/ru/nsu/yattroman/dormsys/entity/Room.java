package ru.nsu.yattroman.dormsys.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "room_number")
    private int roomNumber;
    @Column(name = "dormitory_number")
    private int dormitoryNumber;

    @OneToMany()
    private List<Inhabitant> inhabitants;
    @OneToMany
    private List<Furniture> furnitures;

}
