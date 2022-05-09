package ru.nsu.yattroman.dormsys.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.yattroman.dormsys.DTO.EventDto;
import ru.nsu.yattroman.dormsys.entity.Event;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;
import ru.nsu.yattroman.dormsys.entity.clubs.ClubManager;

@Component
public class EventMapper implements Mapper<Event, EventDto>{

    private final ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Event toEntity(EventDto dto) {
        var event = modelMapper.map(dto, Event.class);
        event.setClub(new Club(dto.getClub().getId()));
        return event;
    }

    @Override
    public EventDto toDTO(Event entity) {
        return modelMapper.map(entity, EventDto.class);
    }
}
