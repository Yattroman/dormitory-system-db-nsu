package ru.nsu.yattroman.dormsys.DTO.club;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.DTO.DTO;
import ru.nsu.yattroman.dormsys.DTO.UserDto;

@Data
@NoArgsConstructor
public class ClubManagerDto implements DTO {

    private UserDto user;

}
