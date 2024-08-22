package doctorBookingApp.controller.appointmentsControllers;

import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import doctorBookingApp.service.bookingServices.TimeSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }


    //ПОЛУЧЕНИЕ СПИСКА СВОБОДНЫХ ВРЕМЕННЫХ СЛОТОВ

   // @Operation(summary = "Получение свободных временнЫх слотов", description = "Возвращает список всех временнЫх слотов, которые еще не забронированы.")
   @Operation(summary = "Retrieve all available time slots")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "List of all available time slots",
                   content = @Content(mediaType = "application/json",
                           schema = @Schema(implementation = TimeSlot.class)))
   })

    @GetMapping
    public List<TimeSlot> getAvailableTimeSlots() {
        return timeSlotService.getAvailableTimeSlots();
    }


    //БРОНИРОВАНИЕ ВРЕМЕННОГО СЛОТА

    @Operation(summary = "Бронирование временного слота")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Дата и время приема успешно забронированы. Для завершения процесса надо нажать кнопку ПОДТВЕРДИТЬ.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "302", description = "Пользователь не аутентифицирован, перенаправление на страницу авторизации.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Временной слот не найден.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Временной слот уже забронирован.",
                    content = @Content(mediaType = "application/json"))
    })

    @PostMapping("/{id}/booking")
    public ResponseEntity<?> bookTimeSlot(@PathVariable Long id, Principal principal) {

        // Проверка на аутентификацию пользователя
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/auth")).build();
        }

        timeSlotService.bookingTimeSlot(id);
        return ResponseEntity.ok().body("Datum und Uhrzeit des Termins wurden erfolgreich reserviert. Wenn Sie sich Ihrer Wahl sicher sind, klicken Sie bitte auf die Schaltfläche BESTÄTIGEN.");
    }                                   //Дата и время приема успешно забронированы. Если Вы уверены в своем выборе, пожалуйста, нажмите кнопку ПОДТВЕРДИТЬ



    //ОТКАЗ ОТ ЗАБРОНИРОВАННОГО ВРЕМЕННОГО СЛОТА
    @Operation(summary = "Отмена бронирования временного слота")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Бронирование даты приема успешно отменено.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Временной слот не найден",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Бронирование даты приема не может быть отменено.",
                    content = @Content(mediaType = "application/json"))
    })

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        timeSlotService.cancelBooking(id);
        return ResponseEntity.ok().body("Die Buchung wurde storniert."); //Бронирование отменено
    }



    // МЕТОДЫ ДЛЯ УПРАВЛЕНИЯ ВРЕМЕННЫМИ СЛОТАМИ

    @Operation(summary = "Add a new time slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time slot successfully added",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimeSlot.class)))
    })

    @PostMapping
    public TimeSlot addTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO) {
        return timeSlotService.addTimeSlot(timeSlotDTO);
    }


    @Operation(summary = "Update an existing time slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time slot successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimeSlot.class)))
    })

    @PutMapping("/{id}")
    public TimeSlot updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlotDTO timeSlotDTO) {
        return timeSlotService.updateTimeSlot(id, timeSlotDTO);
    }


    @Operation(summary = "Delete a time slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Time slot successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Time slot not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimeSlot.class)))
    })

    @DeleteMapping("/{id}")
    public void deleteTimeSlot(@PathVariable Long id) {

        timeSlotService.deleteTimeSlot(id);
    }


    @Operation(summary = "Retrieve time slots by doctor ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of time slots for the specified doctor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimeSlot.class)))
    })

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<TimeSlot>> getTimeSlotsByDoctor(
            @PathVariable Long doctorId,
            @RequestParam(required = false) TypeOfInsurance insurance) {

        try {
            // Получаем список временных слотов от сервиса
            List<TimeSlot> timeSlots = timeSlotService.findByDoctorIdAndInsurance(doctorId, insurance);

            // Возвращаем список временных слотов в успешном ответе
            return ResponseEntity.ok(timeSlots);
        } catch (Exception e) {
            // Обработка ошибок и возврат соответствующего ответа
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @GetMapping
//    public List<TimeSlot> getAvailableTimeSlots() {
//        return timeSlotService.getAvailableTimeSlots();
//    }


    @Operation(summary = "Retrieve time slots by date and time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of time slots for the specified date and time",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimeSlot.class)))
    })

    @GetMapping("/date-time/{dateTime}")
    public List<TimeSlot> getTimeSlotsByDateTime(
            @PathVariable("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return timeSlotService.getTimeSlotsByDateTime(dateTime);
    }



    @Operation(summary = "Retrieve time slots by insurance type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of time slots for the specified insurance type",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimeSlot.class)))
    })

    @GetMapping("/insurance/{insurance}")
    public List<TimeSlot> getTimeSlotsByInsurance(@PathVariable TypeOfInsurance insurance) {
        return timeSlotService.getTimeSlotsByInsurance(insurance);
    }



    @Operation(summary = "Retrieve a time slot by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time slot with the specified ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimeSlot.class))),
            @ApiResponse(responseCode = "404", description = "Time slot not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TimeSlot.class)))
    })

    @GetMapping("/{id}")
    public Optional<TimeSlot> getTimeSlotById(@PathVariable Long id) {

        return timeSlotService.getTimeSlotById(id);
    }


}
