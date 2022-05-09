package ru.nsu.yattroman.dormsys.entity.dormitory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "beds_number", nullable = false)
    private int bedsNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dormitory_id")
    @JsonBackReference
    private Dormitory dormitory;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY, mappedBy = "room")
    private List<Inhabitant> inhabitants;
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY, mappedBy = "room")
    private List<Furniture> furnitures;

    public Room(@NonNull String roomNumber, int bedsNumber) {
        this.roomNumber = roomNumber;
        this.bedsNumber = bedsNumber;
    }

}
