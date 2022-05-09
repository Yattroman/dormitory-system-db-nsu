package ru.nsu.yattroman.dormsys.entity.clubs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nsu.yattroman.dormsys.entity.User;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class ClubManager {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Club> clubs;

    public ClubManager(Long id) {
        this.id = id;
    }
}
