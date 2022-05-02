package ru.nsu.yattroman.dormsys.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.yattroman.dormsys.DTO.dormitory.FurnitureDto;
import ru.nsu.yattroman.dormsys.DTO.dormitory.RoomDto;
import ru.nsu.yattroman.dormsys.entity.dormitory.Room;

import java.util.stream.Collectors;

@Component
public class RoomMapper implements Mapper<Room, RoomDto>{

    private final ModelMapper modelMapper;
    private final FurnitureMapper furnitureMapper;
    private final InhabitantMapper inhabitantMapper;

    @Autowired
    public RoomMapper(ModelMapper modelMapper, FurnitureMapper furnitureMapper, InhabitantMapper inhabitantMapper) {
        this.modelMapper = modelMapper;
        this.furnitureMapper = furnitureMapper;
        this.inhabitantMapper = inhabitantMapper;
    }

    @Override
    public Room toEntity(RoomDto dto) {
        return null;
    }

    @Override
    public RoomDto toDTO(Room entity) {
        RoomDto roomDto = modelMapper.map(entity, RoomDto.class);
        roomDto.setInhabitants(entity.getInhabitants().stream()
                .map(inhabitantMapper::toDTO)
                .collect(Collectors.toList())
        );
        roomDto.setFurnitures(entity.getFurnitures().stream()
                .map(furnitureMapper::toDTO)
                .collect(Collectors.toList())
        );

        return roomDto;
    }
}
