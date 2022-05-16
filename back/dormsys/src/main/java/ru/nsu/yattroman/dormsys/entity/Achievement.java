package ru.nsu.yattroman.dormsys.entity;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import ru.nsu.yattroman.dormsys.entity.dormitory.Inhabitant;

@Entity
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Inhabitant inhabitant;

}
