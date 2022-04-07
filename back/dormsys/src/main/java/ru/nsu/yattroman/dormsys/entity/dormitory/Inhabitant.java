package ru.nsu.yattroman.dormsys.entity.dormitory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.entity.User;

import java.util.List;
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

    @OneToOne(fetch = FetchType.LAZY)
    private Contract contract;
    @OneToMany(mappedBy = "inhabitant")
    private Set<Offense> offenses;
    @OneToMany(mappedBy = "inhabitant")
    private Set<Achievement> achievements;

    @ManyToOne
    private Room room;

}
