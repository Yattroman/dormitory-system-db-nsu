package ru.nsu.yattroman.dormsys.entity.clubs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nsu.yattroman.dormsys.entity.Event;
import ru.nsu.yattroman.dormsys.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    @Column(columnDefinition="TEXT")
    private String description;

    @Column(name = "unique_name")
    private String uniqueName;

    // TODO: add tags
    // TODO: add photo
    // TODO: add meetings time

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private ClubManager clubManager;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Event> events;

    @ManyToMany
    @JoinTable(name = "club_participants",
            joinColumns = @JoinColumn(name = "club_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants;

    public void addParticipant(User user){
        participants.add(user);
        user.getClubs().add(this);
    }

    public Club(Long id) {
        this.id = id;
    }
}
