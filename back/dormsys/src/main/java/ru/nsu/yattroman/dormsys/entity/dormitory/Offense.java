package ru.nsu.yattroman.dormsys.entity.dormitory;

import com.sun.istack.NotNull;
import jakarta.persistence.*;

@Entity
public class Offense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Inhabitant inhabitant;

}
