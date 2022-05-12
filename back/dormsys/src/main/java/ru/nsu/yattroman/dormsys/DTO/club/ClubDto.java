package ru.nsu.yattroman.dormsys.DTO.club;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.DTO.DTO;

@Data
@NoArgsConstructor
public class ClubDto implements DTO {

    private Long id;
    private String name;
    private String description;
    private String uniqueName;
    private Long participantsNumber;
    private ClubManagerDto clubManager;

    public ClubDto(Long id, String name, String uniqueName, Long participantsNumber) {
        this.id = id;
        this.name = name;
        this.uniqueName = uniqueName;
        this.participantsNumber = participantsNumber;
    }
}
