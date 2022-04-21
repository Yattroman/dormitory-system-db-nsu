package ru.nsu.yattroman.dormsys.entity.dormitory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(name = "room_number")
    private String roomNumber;

    @NonNull
    @Column(name = "beds_number")
    private int bedsNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dormitory_id")
    private Dormitory dormitory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Inhabitant> inhabitants;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Furniture> furnitures;

    public Room(@NonNull String roomNumber, int bedsNumber) {
        this.roomNumber = roomNumber;
        this.bedsNumber = bedsNumber;
    }

}
