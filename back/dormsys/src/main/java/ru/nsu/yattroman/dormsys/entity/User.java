package ru.nsu.yattroman.dormsys.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.nsu.yattroman.dormsys.entity.dormitory.DormitoryManager;
import ru.nsu.yattroman.dormsys.entity.dormitory.Inhabitant;
import ru.nsu.yattroman.dormsys.entity.roles.Role;
import ru.nsu.yattroman.dormsys.util.Gender;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String nickname;
    @NonNull
    private String password;

    private String email;
//    @NonNull
    @Column(name = "first_name", length = 50)
    private String firstName;
//    @NonNullc
    @Column(name = "middle_name", length = 50)
    private String middleName;
//    @NonNull
    @Column(name = "surname", length = 50)
    private String surname;
//    @NonNull
    private int age;
//    @NonNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private DormitoryManager dormitoryManager;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Inhabitant inhabitant;

}
