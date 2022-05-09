package ru.nsu.yattroman.dormsys.controller.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InhabitantRequest {
    private Long roomId;
    private Long userId;
    private Long daysToLive;
}
