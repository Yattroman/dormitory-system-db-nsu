package ru.nsu.yattroman.dormsys;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;
import ru.nsu.yattroman.dormsys.service.inerfaces.IClubService;
import ru.nsu.yattroman.dormsys.service.inerfaces.IEventService;
import ru.nsu.yattroman.dormsys.service.inerfaces.IUserService;
import ru.nsu.yattroman.dormsys.util.Gender;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LeisureTest {

    private IClubService clubService;
    private IEventService eventService;
    private IUserService userService;

    @Autowired
    public LeisureTest(IClubService clubService, IEventService eventService, IUserService userService) {
        this.clubService = clubService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Test
    void subscribeToClub_thenUnsubscribeAndCheckData() {
        userService.registerNewUserAccount(new User("admin", "password", "admin@gmail.com",
                        "Anton", "Mikhailovich", "Smith", null, Gender.MALE)
        );
        var user = userService.loadUserByNickname("admin");
        assertThat(user).isNotNull();

        var club = clubService.showClubDetails(2L);
        assertThat(club).isNotNull();
//        clubService.subscribeUserToClub(club.getId(), user.getId());

//        assertThat(clubService.getClubsByUser(user.getId()).size()).isEqualTo(1);
//        assertThat(clubService.getClubsByUser(user.getId()).get(0).getName()).isEqualTo("Секция горного туризма НГУ");
//        assertThat(clubService.getClubsByUser(user.getId()).get(0).getUniqueName()).isEqualTo("hikingclubnsu");
    }
}
