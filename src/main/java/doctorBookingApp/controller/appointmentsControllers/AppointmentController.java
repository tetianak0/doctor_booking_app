package doctorBookingApp.controller.appointmentsControllers;

import doctorBookingApp.entity.Appointment;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.service.bookingServices.AppointmentService;
import doctorBookingApp.service.bookingServices.TimeSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final TimeSlotService timeSlotService;
    private final AppointmentService appointmentService;


    public AppointmentController(TimeSlotService timeSlotService, AppointmentService appointmentService) {
        this.timeSlotService = timeSlotService;
        this.appointmentService = appointmentService;
    }


    // ПОЛУЧЕНИЕ ПОЛЬЗОВАТЕЛЕМ СПИСКА ВСЕХ СВОИХ ЗАПИСЕЙ НА ПРИЕМ К ВРАЧУ

    @Operation(summary = "Получение текущим пользователем своих записей на приём к врачам праксиса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список записей на приём успешно получен.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован.",
                    content = @Content(mediaType = "application/json"))
    })

    @GetMapping("/my")
    public List<Appointment> getUserAppointments(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername(); // т.к. у нас email используется как логин (username) для входа в систему
        return appointmentService.getAppointmentsByUser(email);
    }



// ФИНАЛЬНОЕ ПОДТВЕРДЖЕНИЕ ЗАПИСИ НА ПРИЕМ К ВРАЧУ

    @Operation(summary = "Финальное подтверждение записи на приём", description = "После аутентификации/регистрации пользователь должен окончательно подтвердить свой " +
            " выбор или нажать кнопку ОТМЕНА, если понял, что ему не подходит выбранная дата или время.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запись на приём успешно подтверждена.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Пользователь или временной слот не найден.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Временной слот уже забронирован.",
                    content = @Content(mediaType = "application/json"))
    })

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmAppointment(@RequestParam Long timeSlotId, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        TimeSlot timeSlot = timeSlotService.bookingTimeSlot(timeSlotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Der Zeitfenster wurde nicht gefunden.")); //Временной слот не найден

        appointmentService.confirmAppointment(timeSlot, email);

        return ResponseEntity.ok().body("Der Termin wurde erfolgreich bestätigt."); //Запись на приём успешно подтверждена
    }



    // ПАЦИЕНТ ОТМЕНЯЕТ ЗАПИСЬ НА ПРИЕМ. Админу тоже доступна эта функция

    @Operation(summary = "Отмена пациентом записи на прием", description = "Позволяет пользователю отменить свою запись на прием к врачу, если до приема осталось больше 24 часов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запись успешно отменена.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Запись или пользователь не найдены.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Невозможно отменить запись, так как до приёма осталось меньше 24 часов.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Данный пользователь не имеет прав на отмену этой записи.",
                    content = @Content(mediaType = "application/json"))
    })

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
        appointmentService.cancelAppointment(id, email);

        return ResponseEntity.ok().body("Der Termin wurde erfolgreich storniert."); //Запись на прием успешно отменена
    }

}
