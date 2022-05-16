package ru.nsu.yattroman.dormsys.DTO.metainfo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class EventBoardElement {
    private Long id;
    private String name;
    private String location;
    private String takeTime;
    private Long participantsNumber;
    private String clubOrg;
}
