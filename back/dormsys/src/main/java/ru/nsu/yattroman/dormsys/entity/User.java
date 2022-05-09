package ru.nsu.yattroman.dormsys.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;
import ru.nsu.yattroman.dormsys.entity.clubs.ClubManager;
import ru.nsu.yattroman.dormsys.entity.dormitory.DormitoryManager;
import ru.nsu.yattroman.dormsys.entity.dormitory.Inhabitant;
import ru.nsu.yattroman.dormsys.entity.roles.Role;
import ru.nsu.yattroman.dormsys.util.Gender;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    private String nickname;
    @NonNull
    private String password;

    private String email;
//    @NonNull
    @Column(name = "first_name", length = 50)
    private String firstName;
//    @NonNull
    @Column(name = "middle_name", length = 50)
    private String middleName;
//    @NonNull
    @Column(name = "surname", length = 50)
    private String surname;
//    @NonNull
    private Date dateBirth;
//    @NonNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @ManyToMany
    @JoinTable(name = "users_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;

    @ManyToMany
    @JoinTable(name = "users_clubs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id"))
    private Set<Club> clubs;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private DormitoryManager dormitoryManager;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @PrimaryKeyJoinColumn
    private ClubManager clubManager;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @PrimaryKeyJoinColumn
    private Inhabitant inhabitant;

    public void addClub(Club club){
        clubs.add(club);
        club.getParticipants().add(this);
    }

    public void removeClub(Club club) {
        clubs.removeIf(c -> c.getId().equals(club.getId()));
    }

    public void addEvent(Event event){
        events.add(event);
        event.getParticipants().add(this);
    }

    public void removeEvent(Event event) {
        events.removeIf(c -> c.getId().equals(event.getId()));
    }


}
