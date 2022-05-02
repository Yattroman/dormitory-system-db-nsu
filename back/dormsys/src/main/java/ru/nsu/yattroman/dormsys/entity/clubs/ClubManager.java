package ru.nsu.yattroman.dormsys.entity.clubs;

import jakarta.persistence.*;
import ru.nsu.yattroman.dormsys.entity.User;

@Entity
public class ClubManager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Club club;

}
