package ru.nsu.yattroman.dormsys.entity.roles;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.entity.User;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    private Collection<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }
}
