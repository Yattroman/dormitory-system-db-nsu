package ru.nsu.yattroman.dormsys.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.yattroman.dormsys.DTO.dormitory.FurnitureDto;
import ru.nsu.yattroman.dormsys.entity.dormitory.Furniture;

@Component
public class FurnitureMapper implements Mapper<Furniture, FurnitureDto>{

    private final ModelMapper modelMapper;

    @Autowired
    public FurnitureMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Furniture toEntity(FurnitureDto dto) {
        return modelMapper.map(dto, Furniture.class);
    }

    @Override
    public FurnitureDto toDTO(Furniture entity) {
        return modelMapper.map(entity, FurnitureDto.class);
    }
}
