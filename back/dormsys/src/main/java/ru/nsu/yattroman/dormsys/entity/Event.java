package ru.nsu.yattroman.dormsys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String location;
    private String description;

    @ManyToMany(mappedBy = "events")
    private Set<User> participants;

    @Column(name = "take_time")
    @Temporal(TemporalType.DATE)
    private Date takeTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Club club;

}
