package ru.nsu.yattroman.dormsys.DTO.dormitory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContractDto {

    private Long id;
    private String startTime;
    private String endTime;

}
