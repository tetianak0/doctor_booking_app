package doctorBookingApp.controller;
import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import doctorBookingApp.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

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
