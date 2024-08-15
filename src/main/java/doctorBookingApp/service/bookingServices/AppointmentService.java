package doctorBookingApp.service.bookingServices;

import doctorBookingApp.entity.Appointment;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.AppointmentStatus;
import doctorBookingApp.repository.UserRepository;
import doctorBookingApp.repository.bookingRepositories.AppointmentRepository;
import doctorBookingApp.repository.bookingRepositories.TimeSlotRepository;
import doctorBookingApp.service.registerServices.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired //автоматич. связывание компонентов бина между собой. Позволяет автоматически настраивать свойства бина и методы,
    // упрощая тем самым процесс инъекции зависимостей.
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private MailService mailService;


    //КОНСТРУКТОР APPOINTMENTa (создание записи на прием к врачу - связываем конкретного пользователя с конкретнім временем и врачом)

    public void prepareAppointment(TimeSlot timeSlot, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден."));

        Appointment appointment = Appointment.builder()
                .doctorId(timeSlot.getDoctor().getId())
                .userId(user.getId())
                .timeSlotId(timeSlot.getId())
                .build();

        appointmentRepository.save(appointment);

        sendEmailAboutAppointment(timeSlot, user);

    }

// ОТПРАВКА ПИСЕМ С APPOINTMENTом ПАЦИЕНТУ И В ПРАКСИС

    private void sendEmailAboutAppointment(TimeSlot timeSlot, User user) {
        String clinicEmail = "doctorbooking80@gmail.com"; // должен быть реальный (также прописанный в пропертис) адрес праксиса

        String userSubject = "Подтверждение записи на приём";
        String userMessage = String.format("Уважаемый(ая) %s, ваша запись к доктору %s на %s подтверждена.",
                user.getSurName(), timeSlot.getDoctor().getLastName(), timeSlot.getDateTime().toString());

        String clinicSubject = "Новая запись на приём";
        String clinicMessage = String.format("Господин(жа) %s записан(а) на приём к доктору %s на %s.",
                user.getSurName(), timeSlot.getDoctor().getLastName(), timeSlot.getDateTime()); //тут без .toString(), потому что IDEA делает его серым


        mailService.sendEmailAboutAppointment(user.getEmail(), userSubject, userMessage);
        mailService.sendEmailAboutAppointment(clinicEmail, clinicSubject, clinicMessage);
    }


// ПОЛУЧЕНИЕ ПОЛЬЗОВАТЕЛЕМ СПИСКА ВСЕХ СВОИХ ЗАПИСЕЙ НА ПРИЕМ К ВРАЧУ

    public List<Appointment> getAppointmentsByUser(String Email) {
        User user = userRepository.findByEmail(Email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден."));
        return appointmentRepository.findByUserId(user.getId());
    }



    // ПАЦИЕНТ ОТМЕНЯЕТ ЗАПИСЬ НА ПРИЕМ

    @Transactional
    public void cancelAppointment(Long appointmentId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден."));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Запрашиваемая запись не найдена"));

        if (!appointment.getUserId().equals(user.getId())) {
            throw new SecurityException("Вы не можете отменить эту запись");
        }

        TimeSlot timeSlot = timeSlotRepository.findById(appointment.getTimeSlotId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Запрашиваемое время приема (тайм-слот) не найдено."));

        // ПРОВЕРКА НА ВРЕМЯ
        LocalDateTime now = LocalDateTime.now();
        if (timeSlot.getDateTime().isBefore(now.plusHours(24))) {
            throw new IllegalStateException("До приема у доктора осталось меньше 24 часов. Поэтому Вы уже не можете отменить запись.");
        }

        // ОБНОВЛЯЕМ СТАТУС APPOINTMENTа на "CANCELED"
        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);

        // ТАЙМ-СЛОТ СТАНОВИТСЯ ДОСТУПНЫМ ДЛЯ ДРУГИХ ПОЛЬЗОВАТЕЛЕЙ
        timeSlot.setIsBooked(false);
        timeSlotRepository.save(timeSlot);

        // ОТПРАВКА ПИСЕМ ОБ ОТМЕНЕ ЗАПИСИ НА ПРИЕМ
        String userSubject = "Отмена записи на приём";
        String userMessage = String.format("Уважаемый(ая) %s, ваша запись к доктору %s на %s была отменена.",
                user.getSurName(), timeSlot.getDoctor().getLastName(), timeSlot.getDateTime().toString());
        mailService.sendEmailAboutAppointment(user.getEmail(), userSubject, userMessage);


        String clinicEmail = "doctorbooking80@gmail.com"; // должен быть реальный (также прописанный в пропертис) адрес праксиса
        String clinicSubject = "Отмена записи на приём";
        String clinicMessage = String.format("Господин(жа) %s отменил(а) запись к доктору %s на %s.",
                user.getSurName(), timeSlot.getDoctor().getLastName(), timeSlot.getDateTime().toString());
        mailService.sendEmailAboutAppointment(clinicEmail, clinicSubject, clinicMessage);
    }

}











