package ru.nsu.yattroman.dormsys.controller.request;

import lombok.Data;

@Data
public class FurnitureRequest {

    private Long roomId;
    private Long furnitureId;

}
