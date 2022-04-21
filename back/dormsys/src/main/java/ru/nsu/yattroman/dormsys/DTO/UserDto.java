package ru.nsu.yattroman.dormsys.DTO;

import com.sun.istack.NotNull;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.util.Gender;

@Data
@NoArgsConstructor
public class UserDto {

    @NotNull
    private String nickname;
    @NotNull
    private String firstName;
    @NotNull
    private String middleName;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private String password;

}
