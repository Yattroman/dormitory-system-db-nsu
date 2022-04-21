package ru.nsu.yattroman.dormsys.entity.dormitory;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Dormitory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "dormitory")
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
