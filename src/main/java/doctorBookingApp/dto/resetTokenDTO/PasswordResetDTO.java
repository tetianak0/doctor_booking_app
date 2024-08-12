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
@Schema(name = "ResetPassword", description = "Обновление забытого пароля")
public class PasswordResetDTO {

    private String tokenForResetPassword;
    private String newPassword;
}
