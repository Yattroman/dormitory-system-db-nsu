package ru.nsu.yattroman.dormsys.DTO.dormitory;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.DTO.DTO;

import java.util.List;

@Data
@NoArgsConstructor
public class RoomDto implements DTO {

    private Long id;
    private String roomNumber;
    private int bedsNumber;
    private List<InhabitantDto> inhabitants;
    private List<FurnitureDto> furnitures;

}
