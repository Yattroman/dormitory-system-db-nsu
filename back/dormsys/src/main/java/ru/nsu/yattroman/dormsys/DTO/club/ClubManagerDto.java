package ru.nsu.yattroman.dormsys.DTO.club;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.DTO.DTO;
import ru.nsu.yattroman.dormsys.DTO.UserDto;

import java.util.List;

@Data
@NoArgsConstructor
public class ClubManagerDto implements DTO {

    private Long id;
    private UserDto user;
    private List<ClubDto> clubs;

}
