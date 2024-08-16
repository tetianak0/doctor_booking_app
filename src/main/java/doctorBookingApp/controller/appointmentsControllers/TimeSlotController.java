package doctorBookingApp.controller.appointmentsControllers;

import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import doctorBookingApp.service.bookingServices.TimeSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Operation(summary = "Получение свободных временнЫх слотов", description = "Возвращает список всех временнЫх слотов, которые еще не забронированы.")

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
        return ResponseEntity.ok().body("Дата и время приема успешно забронированы. Если Вы уверены в своем выборе, пожалуйста, нажмите кнопку ПОДТВЕРДИТЬ.");
    }



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
        return ResponseEntity.ok().body("Бронирование отменено.");
    }



    // МЕТОДЫ ДЛЯ УПРАВЛЕНИЯ ВРЕМЕННЫМИ СЛОТАМИ

    @PostMapping
    public TimeSlot addTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO) {
        return timeSlotService.addTimeSlot(timeSlotDTO);
    }

    @PutMapping("/{id}")
    public TimeSlot updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlotDTO timeSlotDTO) {
        return timeSlotService.updateTimeSlot(id, timeSlotDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTimeSlot(@PathVariable Long id) {

        timeSlotService.deleteTimeSlot(id);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<TimeSlot> getTimeSlotsByDoctor(@PathVariable Long doctorId) {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setDoctorId(doctorId);
        return timeSlotService.getTimeSlotsByDoctor(timeSlotDTO);
    }

    @GetMapping("/date-time/{dateTime}")
    public List<TimeSlot> getTimeSlotsByDateTime(
            @PathVariable("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return timeSlotService.getTimeSlotsByDateTime(dateTime);
    }


    @GetMapping("/insurance/{insurance}")
    public List<TimeSlot> getTimeSlotsByInsurance(@PathVariable TypeOfInsurance insurance) {
        return timeSlotService.getTimeSlotsByInsurance(insurance);
    }


    @GetMapping("/{id}")
    public Optional<TimeSlot> getTimeSlotById(@PathVariable Long id) {

        return timeSlotService.getTimeSlotById(id);
    }


}
