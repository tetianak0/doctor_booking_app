package doctorBookingApp.controller;
import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import doctorBookingApp.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/doctor/{doctor_id}")
    public List<TimeSlot> getTimeSlotsByDoctor(@PathVariable Long doctor_id) {
        return timeSlotService.getTimeSlotsByDoctor(doctor_id);
    }

    @GetMapping("/insurance/{insurance}")
    public List<TimeSlot> getTimeSlotsByInsurance(@PathVariable TypeOfInsurance insurance) {
        return timeSlotService.getTimeSlotsByInsurance(insurance);
    }

    @GetMapping("/date/{date_time}")
    public List<TimeSlot> getTimeSlotsByDateTime(@PathVariable Long date_time) {
        return timeSlotService.getTimeSlotsByDateTime(date_time);
    }

    @GetMapping("/{id}")
    public Optional<TimeSlot> getTimeSlotById(@PathVariable Long id) {
        return timeSlotService.getTimeSlotById(id);
    }
}
