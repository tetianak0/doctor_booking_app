package doctorBookingApp.repository.bookingRepositories;


import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {


    /*
    автоматически генерируется Spring Data JPA на основе имени метода. Выполняет
     SQL-запрос, который выбирает все записи из таблицы time_slots,
     где значение столбца is_booked равно false.
      Без параметров, потому что в названии метода заложено условие поиска*/

    List<TimeSlot> findByIsBookedFalse();
    Optional<TimeSlot> findById(Long id);


    List<TimeSlot> findByDoctorId(Long doctorId);
    List<TimeSlot> findByInsurance(TypeOfInsurance insurance);
    List<TimeSlot> findByDateTime(LocalDateTime dateTime);




}