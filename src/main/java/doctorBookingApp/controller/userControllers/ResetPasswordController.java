package doctorBookingApp.controller.userControllers;

import doctorBookingApp.dto.StandardResponseDto;
import doctorBookingApp.dto.resetTokenDTO.PasswordResetDTO;
import doctorBookingApp.dto.resetTokenDTO.PasswordResetRequestDTO;
import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.service.userServices.ResetPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//Контроллер обработки HTTP-запросов, которые приходят от пользователя для замена забытого пароля.

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth-for-reset")
public class ResetPasswordController {

  private final ResetPasswordService resetPasswordService;

    @Operation(summary = "Отправление ссылки для восстановления пароля к приложению")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ссылка для восстановления пароля отправлена на вашу электронную почту.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Невозможно отправить ссылку для восстановления пароля.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))

    })


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody PasswordResetRequestDTO requestDTO) throws MessagingException {
        resetPasswordService.sendResetPasswordLink(requestDTO.getEmail());
        return ResponseEntity.ok("Ссылка для восстановления пароля отправлена на вашу электронную почту.");
    }



    @Operation(summary = "Восстановления пароля по токену. Проверяет валидность токена для восстановления пароля и предлагает ввести новый пароль.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Введите новый пароль.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Неверный токен или срок его действия истек.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))

    })



    @GetMapping("/reset-password")
    public ResponseEntity<String> showResetPasswordPage(@RequestParam("token") String tokenForPassword) {

        boolean isValid = resetPasswordService.validateResetPasswordToken(tokenForPassword); // валидация токена
        if (isValid) {
            return ResponseEntity.ok("Введите новый пароль.");
        }else {
            return ResponseEntity.badRequest().body("Неверный токен или срок его действия истек.");
        }
    }


   @Operation(summary = "Подтверждение обновления пароля")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Пароль успешно обновлен.",
                   content = @Content(mediaType = "application/json",
                           schema = @Schema(implementation = UserDTO.class))),
           @ApiResponse(responseCode = "400", description = "Ошибка при обновлении пароля.",
                   content = @Content(mediaType = "application/json",
                           schema = @Schema(implementation = StandardResponseDto.class)))

   })


 @PostMapping("/reset-password")
  public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {

       boolean isReset = resetPasswordService.updatePassword(passwordResetDTO.getTokenForResetPassword(), passwordResetDTO.getNewPassword());

       if (isReset) {
           return ResponseEntity.ok("Пароль успешно обновлен.");
       } else {
           return ResponseEntity.badRequest().body("Ошибка при обновлении пароля.");
       }

   }
}


