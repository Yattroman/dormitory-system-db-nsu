package ru.nsu.yattroman.dormsys.DTO.club;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.DTO.DTO;

@Data
@NoArgsConstructor
public class ClubDto implements DTO {

    private String name;
    private String description;
    private String uniqueName;
//    private ClubManagerDto clubManagerDto;

}
