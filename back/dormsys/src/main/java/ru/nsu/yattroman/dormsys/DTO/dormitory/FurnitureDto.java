package ru.nsu.yattroman.dormsys.DTO.dormitory;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.DTO.DTO;

@Data
@NoArgsConstructor
public class FurnitureDto implements DTO {

    private Long id;
    private String name;
    private String innerCode;
    private String category;

}
