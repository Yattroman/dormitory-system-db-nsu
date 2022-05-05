package ru.nsu.yattroman.dormsys.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.yattroman.dormsys.DTO.EventDto;
import ru.nsu.yattroman.dormsys.entity.Event;

@Component
public class EventMapper implements Mapper<Event, EventDto>{

    private final ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Event toEntity(EventDto dto) {
        return modelMapper.map(dto, Event.class);
    }

    @Override
    public EventDto toDTO(Event entity) {
        return modelMapper.map(entity, EventDto.class);
    }
}
