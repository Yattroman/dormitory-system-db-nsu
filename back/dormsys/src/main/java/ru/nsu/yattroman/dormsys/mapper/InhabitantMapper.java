package ru.nsu.yattroman.dormsys.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.yattroman.dormsys.DTO.AchievementDto;
import ru.nsu.yattroman.dormsys.DTO.OffenseDto;
import ru.nsu.yattroman.dormsys.DTO.dormitory.ContractDto;
import ru.nsu.yattroman.dormsys.DTO.dormitory.InhabitantDto;
import ru.nsu.yattroman.dormsys.entity.dormitory.Inhabitant;

import java.util.stream.Collectors;

@Component
public class InhabitantMapper implements Mapper<Inhabitant, InhabitantDto>{

    private final UserMapper userMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public InhabitantMapper(UserMapper userMapper, ModelMapper modelMapper) {
        this.userMapper = userMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public Inhabitant toEntity(InhabitantDto dto) {
        return null;
    }

    @Override
    public InhabitantDto toDTO(Inhabitant entity) {
        var inhabitantDto = modelMapper.map(entity, InhabitantDto.class);
        inhabitantDto.setUser(userMapper.toDTO(entity.getUser()));
        inhabitantDto.setOffenses(entity.getOffenses().stream()
                .map(offense -> modelMapper.map(offense, OffenseDto.class)).collect(Collectors.toList()));
        inhabitantDto.setAchievements(entity.getOffenses().stream()
                .map(achievement -> modelMapper.map(achievement, AchievementDto.class)).collect(Collectors.toList()));
        inhabitantDto.setContract(modelMapper.map(entity.getContract(), ContractDto.class));
        return inhabitantDto;
    }
}
