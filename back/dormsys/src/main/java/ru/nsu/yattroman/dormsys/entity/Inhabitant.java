package ru.nsu.yattroman.dormsys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Inhabitant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_dormitory_inhabitant")
    private boolean isDormitoryInhabitant;

    @OneToOne(cascade = CascadeType.ALL)
    private Student studentInfo;

}
