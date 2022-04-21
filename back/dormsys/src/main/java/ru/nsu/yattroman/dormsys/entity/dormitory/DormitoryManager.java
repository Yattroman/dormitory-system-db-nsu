package ru.nsu.yattroman.dormsys.entity.dormitory;

import jakarta.persistence.*;
import ru.nsu.yattroman.dormsys.entity.User;

@Entity
public class DormitoryManager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Dormitory dormitory;

}
