package ru.nsu.yattroman.dormsys.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.yattroman.dormsys.DTO.club.ClubDto;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;
import ru.nsu.yattroman.dormsys.service.inerfaces.IClubService;

@Component
public class ClubMapper implements Mapper<Club, ClubDto> {

    private final ModelMapper modelMapper;
    private final IClubService clubService;

    @Autowired
    public ClubMapper(ModelMapper modelMapper, IClubService clubService) {
        this.modelMapper = modelMapper;
        this.clubService = clubService;
    }

    @Override
    public Club toEntity(ClubDto dto) {
        var club = modelMapper.map(dto, Club.class);
//        club.setClubManager(clubService.getClubManager(dto.getClubManagerId()));
        // Add club manager
        return club;
    }

    @Override
    public ClubDto toDTO(Club entity) {
        var clubDto = modelMapper.map(entity, ClubDto.class);
        return clubDto;
    }
}
