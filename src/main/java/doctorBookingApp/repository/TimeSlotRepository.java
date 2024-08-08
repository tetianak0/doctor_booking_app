package doctorBookingApp.repository;


import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {


//        TimeSlot findByIdAndIsBookedFalse(Long id);



    List<TimeSlot> findByDoctorId(Long doctorId);
    List<TimeSlot> findByInsurance(TypeOfInsurance insurance);
    List<TimeSlot> findByDateTime(LocalDateTime dateTime);




}