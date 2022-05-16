package ru.nsu.yattroman.dormsys.entity.dormitory;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Furniture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "inner_code", nullable = false)
    private String innerCode;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Room room;

}
