package ru.nsu.yattroman.dormsys.DTO.dormitory;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.DTO.AchievementDto;
import ru.nsu.yattroman.dormsys.DTO.DTO;
import ru.nsu.yattroman.dormsys.DTO.OffenseDto;
import ru.nsu.yattroman.dormsys.DTO.UserDto;

import java.util.List;

@Data
@NoArgsConstructor
public class InhabitantDto implements DTO {

    private Long id;
    private UserDto user;
    private ContractDto contract;
    private List<OffenseDto> offenses;
    private List<AchievementDto> achievements;

}
