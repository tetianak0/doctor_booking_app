package doctorBookingApp.dto.resetTokenDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "EmailForPassword", description = "Email пользователя для обновления пароля")
public class PasswordResetRequestDTO {

    private String email;

}
