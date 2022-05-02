package ru.nsu.yattroman.dormsys.entity.dormitory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.entity.Achievement;
import ru.nsu.yattroman.dormsys.entity.Offense;
import ru.nsu.yattroman.dormsys.entity.User;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Inhabitant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Contract contract;
    @OneToMany(mappedBy = "inhabitant")
    private Set<Offense> offenses;
    @OneToMany(mappedBy = "inhabitant")
    private Set<Achievement> achievements;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Room room;

}
