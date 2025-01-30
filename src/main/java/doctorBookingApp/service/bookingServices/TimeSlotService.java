package doctorBookingApp.service.bookingServices;


import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotService {

    TimeSlot addTimeSlot(TimeSlotDTO timeSlotDTO);
    TimeSlot updateTimeSlot(Long id, TimeSlotDTO timeSlotDTO);
    void deleteTimeSlot(Long id);
    List<TimeSlot> findByDoctorIdAndInsurance(Long doctorId, TypeOfInsurance insurance);;
    List<TimeSlot> getTimeSlotsByInsurance(TypeOfInsurance insurance);
    List<TimeSlot> getTimeSlotsByDateTime(LocalDateTime dateTime);
    Optional<TimeSlot> getTimeSlotById(Long id);
    List<TimeSlot> getAvailableTimeSlots();
    Optional<TimeSlot> bookingTimeSlot(Long timeSlotId);
    void cancelBooking(Long id);
}
