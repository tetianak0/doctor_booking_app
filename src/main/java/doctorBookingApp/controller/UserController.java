 package doctorBookingApp.controller;

 import doctorBookingApp.dto.StandardResponseDto;
 import doctorBookingApp.dto.usersDTO.NewUserDTO;
 import doctorBookingApp.dto.usersDTO.UserDTO;
 import doctorBookingApp.exeption.RestException;
 import doctorBookingApp.service.userService.ConfirmationCodeService;
 import doctorBookingApp.service.userService.RegistrationUserService;
 import doctorBookingApp.service.userService.UserService;
 import io.swagger.v3.oas.annotations.Operation;
 import io.swagger.v3.oas.annotations.media.Content;
 import io.swagger.v3.oas.annotations.media.Schema;
 import io.swagger.v3.oas.annotations.responses.ApiResponse;
 import io.swagger.v3.oas.annotations.responses.ApiResponses;
 import jakarta.mail.MessagingException;
 import lombok.RequiredArgsConstructor;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.*;

 @RestController
 @RequiredArgsConstructor
 @RequestMapping("/users")
 public class UserController {

     private final UserService userService;
     private final ConfirmationCodeService confirmationCodeService;
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

          @Operation(summary = "Регистрация. Подтверждение пользователя по конфирмационному коду")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Пользователь успешно подтвержден",
                     content = @Content(mediaType = "application/json")),
             @ApiResponse(responseCode = "400", description = "Код не найден или срок его действия истек",
                     content = @Content(mediaType = "application/json"))
     })


     @PostMapping("/confirm")
     public ResponseEntity<?> confirmUser(@RequestParam String confirmationCode) {
         try {
             registrationUserService.confirmUser(confirmationCode);
             return ResponseEntity.ok("Пользователь успешно подтвержден");
         } catch (RestException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Код не найден или срок его действия истек");
         }
     }

     //@RestController возвращает данные непосредственно в виде JSON/XML, а не перенаправляет на HTML страницу.
     //Для перенаправления на HTML страницу лучше использовать @Controller.  ???????????? надо ли менять

     @GetMapping("/confirm")
     public String confirmUserPage(@RequestParam String confirmationCode, Model model) {
         model.addAttribute("confirmationCode", confirmationCode);
         return "confirm"; // Имя HTML страницы подтверждения
     }


     //ПОЛУЧЕНИЕ ИНФОРМАЦИИ О ПОЛЬЗОВАТЕЛЕ

     @Operation(summary = "Получение информации о пользователе по ID")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
             @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))

     })

     @GetMapping("/{userId}")
     public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) throws RestException {
         return ResponseEntity.ok(userService.getUserById(userId));
     }



     @Operation(summary = "Обновление информации о пользователе")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Информация о пользователе обновлена.",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = UserDTO.class))),
             @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = StandardResponseDto.class)))

     })


     @PutMapping("/{userId}")
     public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws RestException {
         return ResponseEntity.ok(userService.editUser(userId, userDTO));
     }


     @Operation(summary = "Получение информации о пользователе по email")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = UserDTO.class))),
             @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = StandardResponseDto.class)))

     })

     @PutMapping("/{email}")
     public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) throws RestException {
         return ResponseEntity.ok(userService.getUserByEmail(email));
     }



     @Operation(summary = "Получение информации о пользователе по номеру телефона")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = UserDTO.class))),
             @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = StandardResponseDto.class)))

     })

     @PutMapping("/{phoneNumber}")
     public ResponseEntity<UserDTO> getUserByPhoneNumber(@PathVariable String phoneNumber) throws RestException {
         return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber));
     }


        //УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ

    @Operation(summary = "Удаление пользователя по email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь удален."),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

     @DeleteMapping("/by-email/{email}")
     public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) throws RestException {
         userService.deleteUserByEmail(email);
         return ResponseEntity.noContent().build();
     }



     @Operation(summary = "Удаление пользователя по номеру телефона")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "204", description = "Пользователь удален."),
             @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = StandardResponseDto.class)))
     })

     @DeleteMapping("/by-phone/{phoneNumber}")
     public ResponseEntity<Void> deleteUserByPhoneNumber(@PathVariable String phoneNumber) throws RestException {
         userService.deleteUserByPhoneNumber(phoneNumber);
         return ResponseEntity.noContent().build();
     }








 }















