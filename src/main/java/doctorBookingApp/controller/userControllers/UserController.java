 package doctorBookingApp.controller.userControllers;

 import doctorBookingApp.dto.StandardResponseDto;
 import doctorBookingApp.dto.usersDTO.UserDTO;
 import doctorBookingApp.entity.User;
 import doctorBookingApp.exeption.RestException;
 import doctorBookingApp.service.userServices.UserService;
 import io.swagger.v3.oas.annotations.Operation;
 import io.swagger.v3.oas.annotations.media.Content;
 import io.swagger.v3.oas.annotations.media.Schema;
 import io.swagger.v3.oas.annotations.responses.ApiResponse;
 import io.swagger.v3.oas.annotations.responses.ApiResponses;
 import lombok.RequiredArgsConstructor;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;

 @RestController
 @RequiredArgsConstructor
 @RequestMapping("/users")
 public class UserController {

     private final UserService userService;


     //ОБНОВЛЕНИЕ ИНФОРМАЦИИ О ПОЛЬЗОВАТЕЛЕ

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

     @GetMapping("/id/{userId}")
     public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) throws RestException {
         return ResponseEntity.ok(userService.getUserById(userId));
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

     @GetMapping("/email/{email}")
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

     @GetMapping("/phone/{phoneNumber}")
     public ResponseEntity<UserDTO> getUserByPhoneNumber(@PathVariable String phoneNumber) throws RestException {
         return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber));
     }

     @GetMapping
     public List<User> getAllUsers() {
         return userService.getAllUsers();
     }



     //УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ

     @Operation(summary = "Удаление пользователя по ID")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "204", description = "Пользователь удален."),
             @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = StandardResponseDto.class)))
     })

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Long id) throws RestException {
        userService.deleteUserById(id);
    }


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















