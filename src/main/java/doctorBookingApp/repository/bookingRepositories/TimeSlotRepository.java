package doctorBookingApp.repository.bookingRepositories;


import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {


    /*
    автоматически генерируется Spring Data JPA на основе имени метода. Выполняет SQL-запрос,
    который выбирает все записи из таблицы time_slots, где значение столбца is_booked равно false.
      Без параметров, потому что в названии метода заложено условие поиска*/

    List<TimeSlot> findByIsBookedFalse();

//    List<TimeSlot> findByDoctorId(Long doctorId);  // Поиск по doctorId
//
//    List<TimeSlot> findByDoctorIdAndInsurance(Long doctorId, TypeOfInsurance insurance); // Поиск по doctorId и insurance

    List<TimeSlot> findByInsurance(TypeOfInsurance insurance);

    List<TimeSlot> findByDateTime(LocalDateTime dateTime);

    List<TimeSlot> findByDoctorIdAndInsuranceAndIsBookedFalse(Long doctorId, TypeOfInsurance insurance);

    List<TimeSlot> findByDoctorIdAndIsBookedFalse(Long doctorId);
}



