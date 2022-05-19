package ru.nsu.yattroman.dormsys.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.yattroman.dormsys.util.Gender;
import ru.nsu.yattroman.dormsys.validator.annotation.ValidEmail;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto implements DTO{
    private Long id;
    @NotBlank(message = "Nickname is mandatory")
    @Size(min = 3, max = 30, message = "Nickname length must be >={min}, <={max}")
    private String nickname;
    @NotBlank(message = "Firstname is mandatory")
    private String firstName;
    private String middleName;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    @NotBlank(message = "Email is mandatory")
    @ValidEmail
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date dateBirth;
    @Size(min = 5, max = 30, message = "Password length must be >={min}, <={max}")
    private String password;
    private List<String> roles;
}
