package ru.nsu.yattroman.dormsys.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.hibernate.annotations.Check;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;
import ru.nsu.yattroman.dormsys.entity.clubs.ClubManager;
import ru.nsu.yattroman.dormsys.entity.dormitory.DormitoryManager;
import ru.nsu.yattroman.dormsys.entity.dormitory.Inhabitant;
import ru.nsu.yattroman.dormsys.entity.roles.Role;
import ru.nsu.yattroman.dormsys.util.Gender;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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
    @Column(nullable = false,
            columnDefinition = "varchar(50) check(char_length(nickname) > 2 and char_length(nickname) < 30)")
    private String nickname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @Column(name = "middle_name", length = 50)
    private String middleName;
    @Column(name = "surname", length = 50, nullable = false)
    private String surname;
    private Date dateBirth;
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
    private Set<Event> events = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "users_clubs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id"))
    private Set<Club> clubs = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private DormitoryManager dormitoryManager;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @PrimaryKeyJoinColumn
    private ClubManager clubManager;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @PrimaryKeyJoinColumn
    private Inhabitant inhabitant;

    public User(String nickname, String password, String email, String firstName, String middleName, String surname, Date dateBirth, Gender gender) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.surname = surname;
        this.dateBirth = dateBirth;
        this.gender = gender;
    }

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

    public User(@NonNull String nickname, @NonNull String password, String email, String firstName, String surname, Date dateBirth, Gender gender) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.dateBirth = dateBirth;
        this.gender = gender;
    }
}
