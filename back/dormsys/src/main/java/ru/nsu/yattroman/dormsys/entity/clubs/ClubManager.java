package ru.nsu.yattroman.dormsys.entity.clubs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.yattroman.dormsys.entity.User;

import java.util.List;

@Entity
@Setter
@Getter
public class ClubManager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Club> clubs;

}
