package doctorBookingApp.service;

import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;

import java.util.List;
import java.util.Optional;

public interface TimeSlotService {
    TimeSlot addTimeSlot(TimeSlotDTO timeSlotDTO);
    TimeSlot updateTimeSlot(Long id, TimeSlotDTO timeSlotDTO);
    void deleteTimeSlot(Long id);
    List<TimeSlot> getTimeSlotsByDoctor(Long doctor_id);
    List<TimeSlot> getTimeSlotsByInsurance(TypeOfInsurance insurance);
    List<TimeSlot> getTimeSlotsByDateTime(Long date_time);
    Optional<TimeSlot> getTimeSlotById(Long id);
}