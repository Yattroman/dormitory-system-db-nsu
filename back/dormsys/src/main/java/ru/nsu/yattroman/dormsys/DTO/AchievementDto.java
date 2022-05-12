package ru.nsu.yattroman.dormsys.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AchievementDto {
    private Long id;
    private String name;
    private String description;
}
