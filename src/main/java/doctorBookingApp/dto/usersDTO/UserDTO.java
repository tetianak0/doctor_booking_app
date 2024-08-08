package doctorBookingApp.dto.usersDTO;


import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "User", description = "Данные пользователя")

public class UserDTO {


    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Thomas")
    private String firstname;

    @Schema(description = "Фамилия пользователя", example = "Mann")
    private String surName;

    @Schema(description = "Дата рождения пользователя", example = "01/01/1950")
    private String birthDate;

    @Schema(description = "Номер телефона пользователя", example = "+4912345678909")
    private String phoneNumber;

    @Schema(description = "Email пользователя", example = "user@mail.com")
    private String email;

    @Schema(description = "Роль пользователя", example = "USER")
    private Role role;


 
    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .surName(user.getSurName())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }


  
    public static List<UserDTO> from(Collection<User> users) {

        return users.stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());

    }


}
