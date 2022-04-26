package ru.nsu.yattroman.dormsys.mapper;

import ru.nsu.yattroman.dormsys.DTO.DTO;

public interface Mapper<E, D extends DTO>{

    E toEntity(D dto);
    D toDTO(E entity);

}
