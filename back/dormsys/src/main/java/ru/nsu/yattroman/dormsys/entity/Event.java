package ru.nsu.yattroman.dormsys.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String location;
    private String description;

    @ManyToMany(mappedBy = "events")
    private List<User> participants;

    @Column(name = "take_time")
    @Temporal(TemporalType.DATE)
    private Date takeTime;

}
