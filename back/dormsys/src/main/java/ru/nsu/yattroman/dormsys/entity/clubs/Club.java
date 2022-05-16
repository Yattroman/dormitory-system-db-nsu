package ru.nsu.yattroman.dormsys.entity.clubs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nsu.yattroman.dormsys.DTO.metainfo.ClubEventsAvg;
import ru.nsu.yattroman.dormsys.entity.Event;
import ru.nsu.yattroman.dormsys.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@NamedNativeQuery(
        name = "find_club_events_avg",
        query =
                "with event_info_extended as ( " +
                        "select e.name, e.location, e.take_time, c.name as club_name, count(u) as participants " +
                        "from event e " +
                        "inner join club c on e.club_id = c.id " +
                        "inner join users_events ue on e.id = ue.event_id " +
                        "inner join sys_user u on ue.user_id = u.id " +
                        "group by e.name, c.name, e.location, e.take_time " +
                        "order by c.name, participants desc " +
                        ") " +
                        "select distinct club_name as clubName, round(avg(participants) over (partition by club_name), 0) as avgParticipantsNumber, " +
                        "                count(participants) over (partition by club_name) as participantsNumber " +
                        "from event_info_extended",
        resultSetMapping = "club_events_avg"
)
@SqlResultSetMapping(
        name = "club_events_avg",
        classes = @ConstructorResult(
                targetClass = ClubEventsAvg.class,
                columns = {
                        @ColumnResult(name = "clubName", type = String.class),
                        @ColumnResult(name = "avgParticipantsNumber", type = Double.class),
                        @ColumnResult(name = "participantsNumber", type = Long.class)
                }
        )
)
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(columnDefinition="TEXT")
    private String description;

    @Column(name = "unique_name", nullable = false)
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
