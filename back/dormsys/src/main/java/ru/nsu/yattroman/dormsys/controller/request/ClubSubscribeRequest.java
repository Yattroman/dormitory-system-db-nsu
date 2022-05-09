package ru.nsu.yattroman.dormsys.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubSubscribeRequest {
    private Long clubId;
    private Long userId;
}
