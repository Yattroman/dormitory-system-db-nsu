package ru.nsu.yattroman.dormsys.DTO.metainfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ClubEventsAvg {
    private String clubName;
    private Double avgParticipantsNumber;
    private Long eventsNumber;
}
