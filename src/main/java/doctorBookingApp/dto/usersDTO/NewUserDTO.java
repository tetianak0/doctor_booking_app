package doctorBookingApp.dto.usersDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "NewUser", description = "Данные для регистрации пользователя")

public class NewUserDTO {

        @NonNull
        private String firstname;

        @NonNull
        private String surName;

        @NonNull
        private String birthDate;

        @NonNull
        private String phoneNumber;

        @Email
        @NonNull
        private String email;

        @NonNull
        @Pattern(regexp = "(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
        private String password;

}
