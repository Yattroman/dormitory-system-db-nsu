package ru.nsu.yattroman.dormsys.DTO;

import com.sun.istack.NotNull;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.util.Gender;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto implements DTO{

    private Long id;
    private String nickname;
    private String firstName;
    private String middleName;
    private String surname;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date dateBirth;
    private String password;
    private List<String> roles;

}
