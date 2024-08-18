package doctorBookingApp.controller.authenticationControllers;

import doctorBookingApp.dto.usersDTO.NewUserDTO;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.service.authenticationServices.RegistrationUserService;
import doctorBookingApp.service.userServices.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users-authentication")
public class RegistrationControllers {

    private final UserService userService;
    private final RegistrationUserService registrationUserService;


    //РЕГИСТРАЦИЯ ПОЛЬЗОВАТЕЛЯ

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация практически завершена, проверьте свою электронную почту.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким email уже существует",
                    content = @Content(mediaType = "application/json"))
    })

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody NewUserDTO newUser) throws RestException, MessagingException {
        registrationUserService.registrationUser(newUser);
        return ResponseEntity.ok("Регистрация практически завершена. Проверьте свой электронный почтовый ящик на наличие кода подтверждения.");
    }


    @GetMapping("/confirm")
    public ResponseEntity<?> confirmUser(@RequestParam String confirmationCode) {
        try {
            registrationUserService.confirmUser(confirmationCode);
            return ResponseEntity.ok("Пользователь успешно подтвержден");
        } catch (RestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Код не найден или срок его действия истек");
        }

    }
}
