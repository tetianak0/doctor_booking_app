package doctorBookingApp.repository.bookingRepositories;

import doctorBookingApp.entity.Appointment;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByUserId(Long userId);

   Optional<Appointment> findByTimeSlotId(Long timeSlotId);
//
//    List<Appointment> findByUser(User user);
//
//    List<Appointment> findByTimeSlot(TimeSlot timeSlot);
//
//    List<Appointment> findByStatus(AppointmentStatus status);


}
