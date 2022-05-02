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

    @NotNull
    @Column(name = "inner_code")
    private String innerCode;
    @NotNull
    private String name;
    @NotNull
    private String category;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Room room;

}
