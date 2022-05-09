package ru.nsu.yattroman.dormsys.controller.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FurnitureRequest {
    private Long roomId;
    private Long furnitureId;
}
