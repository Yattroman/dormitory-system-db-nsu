package ru.nsu.yattroman.dormsys.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.DTO.club.ClubDto;

import java.util.List;

@Data
@NoArgsConstructor
public class EventDto implements DTO{

    private Long id;
    private String name;
    private String location;
    private String description;
    private String takeTime;

    private List<UserDto> participants;
    private ClubDto club;

}
