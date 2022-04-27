package ru.nsu.yattroman.dormsys.entity.dormitory;

import com.sun.istack.NotNull;
import jakarta.persistence.*;

@Entity
public class Furniture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "inner_code")
    private String innerCode;
    @NotNull
    private String name;
    @NotNull
    private int count;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Room room;

}
