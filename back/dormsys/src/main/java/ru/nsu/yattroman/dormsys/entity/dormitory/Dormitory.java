package ru.nsu.yattroman.dormsys.entity.dormitory;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dormitory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "dormitory",
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<Room> rooms;

    // TODO: another cascade
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DormitoryManager manager;

    public Dormitory(String name) {
        this.name = name;
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

}
