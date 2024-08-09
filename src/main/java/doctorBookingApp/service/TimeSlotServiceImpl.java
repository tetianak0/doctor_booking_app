package doctorBookingApp.service;


import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.entity.DoctorProfile;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import doctorBookingApp.repository.DoctorProfileRepository;
import doctorBookingApp.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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