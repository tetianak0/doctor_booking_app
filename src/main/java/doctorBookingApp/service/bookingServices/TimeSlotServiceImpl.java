package doctorBookingApp.service.bookingServices;


import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.entity.DoctorProfile;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import doctorBookingApp.repository.DoctorProfileRepository;
import doctorBookingApp.repository.bookingRepositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    private final DoctorProfileRepository doctorProfileRepository;



    public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository, DoctorProfileRepository doctorProfileRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.doctorProfileRepository = doctorProfileRepository;

    }


// МЕТОДЫ ДЛЯ БРОНИРОВАНИЯ ВРЕМЕНИ ПРИЕМА У ВРАЧА

    public List<TimeSlot> getAvailableTimeSlots() {
        return timeSlotRepository.findByIsBookedFalse();
    }


    @Transactional
    public Optional<TimeSlot> bookingTimeSlot(Long timeSlotId) {
        return timeSlotRepository.findById(timeSlotId)
                .map(timeSlot -> {
                    if (Boolean.TRUE.equals(timeSlot.getIsBooked())) {
                        throw new IllegalStateException("Временной слот уже забронирован.");
                    }

                    timeSlot.setIsBooked(true);
                    return timeSlotRepository.save(timeSlot);
                });


    }

    //ОТКАЗ ОТ ЗАБРОНИРОВАННОГО ВРЕМЕННОГО СЛОТА
    @Transactional
    public void cancelBooking(Long timeSlotId) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Временной слот не найден"));

        timeSlot.setIsBooked(false);
        timeSlotRepository.save(timeSlot);
    }




// МЕТОДЫ ДЛЯ УПРАВЛЕНИЯ ВРЕМЕННЫМИ СЛОТАМИ

    @Override
    public TimeSlot addTimeSlot(TimeSlotDTO timeSlotDTO) {

        // Найти профиль доктора по ID
        DoctorProfile doctor = doctorProfileRepository.findById(timeSlotDTO.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + timeSlotDTO.getDoctorId()));

        // Создать новый TimeSlot и установить свойства
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDoctor(doctor);
        timeSlot.setDateTime(timeSlotDTO.getDateTime());
        timeSlot.setInsurance(timeSlotDTO.getInsurance());
        // Устанавливаем isBooked по умолчанию как false
        timeSlot.setIsBooked(false);

        return timeSlotRepository.save(timeSlot);
    }

    @Override
    public TimeSlot updateTimeSlot(Long id, TimeSlotDTO timeSlotDTO) {
        TimeSlot existingTimeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TimeSlot not found"));

        DoctorProfile doctor = doctorProfileRepository.findById(timeSlotDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        existingTimeSlot.setDoctor(doctor);
        existingTimeSlot.setDateTime(timeSlotDTO.getDateTime());
        existingTimeSlot.setInsurance(timeSlotDTO.getInsurance());
        existingTimeSlot.setIsBooked(timeSlotDTO.getIsBooked());

        return timeSlotRepository.save(existingTimeSlot);
    }

    @Override
    public void deleteTimeSlot(Long id) {

        timeSlotRepository.deleteById(id);
    }


    @Override
    public List<TimeSlot> getTimeSlotsByDoctor(TimeSlotDTO timeSlotDTO) {
        Long doctorId = timeSlotDTO.getDoctorId();
        return timeSlotRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<TimeSlot> getTimeSlotsByInsurance(TypeOfInsurance insurance) {
        return timeSlotRepository.findByInsurance(insurance);
    }

    @Override
    public List<TimeSlot> getTimeSlotsByDateTime(LocalDateTime dateTime) {
        return timeSlotRepository.findByDateTime(dateTime);
    }

    @Override
    public Optional<TimeSlot> getTimeSlotById(Long id) {

        return timeSlotRepository.findById(id);
    }
}