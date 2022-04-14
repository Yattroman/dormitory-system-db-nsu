package ru.nsu.yattroman.dormsys.DTO;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    @NotNull
    private String nickname;
    @NotNull
    private String firstName;
    @NotNull
    private String secondName;
    @NotNull
    private String email;

    @NotNull
    private String password;
    private String matchingPassword;

}
