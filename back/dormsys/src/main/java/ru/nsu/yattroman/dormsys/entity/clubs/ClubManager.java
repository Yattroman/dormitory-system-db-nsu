package ru.nsu.yattroman.dormsys.entity.clubs;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ClubManager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "club_manager_id")
    private Long id;

}
