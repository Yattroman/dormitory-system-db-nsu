package ru.nsu.yattroman.dormsys.controller.request;

import lombok.Data;

@Data
public class InhabitantRequest {

    private Long roomId;
    private Long userId;
    private Long daysToLive;

}
