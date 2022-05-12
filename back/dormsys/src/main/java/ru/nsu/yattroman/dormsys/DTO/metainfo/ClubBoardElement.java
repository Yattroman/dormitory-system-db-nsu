package ru.nsu.yattroman.dormsys.DTO.metainfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClubBoardElement {
    private Long id;
    private String name;
    private String uniqueName;
    private Long participantsNumber;
    private String clubManager;
}
