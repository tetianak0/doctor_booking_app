package doctorBookingApp.service.bookingServices;

import doctorBookingApp.entity.Appointment;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.AppointmentStatus;
import doctorBookingApp.repository.userRepositories.UserRepository;
import doctorBookingApp.repository.bookingRepositories.AppointmentRepository;
import doctorBookingApp.repository.bookingRepositories.TimeSlotRepository;
import doctorBookingApp.service.authenticationServices.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository, TimeSlotRepository timeSlotRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.timeSlotRepository = timeSlotRepository;
    }



    //КОНСТРУКТОР APPOINTMENT-a (создание записи на прием к врачу - связываем конкретного пользователя с конкретнім временем и врачом)
    @PreAuthorize("hasRole('PATIENT')")
    public void confirmAppointment(TimeSlot timeSlot, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer nicht gefunden.")); //Пользователь не найден

        Appointment appointment = Appointment.builder()
                .timeSlot(timeSlot)
                .user(user)
                .status(AppointmentStatus.SCHEDULED)
                .build();

        appointmentRepository.save(appointment);

        sendEmailAboutAppointment(appointment);

    }


// ОТПРАВКА ПИСЕМ С APPOINTMENT-ом ПАЦИЕНТУ И В ПРАКСИС

    private void sendEmailAboutAppointment(Appointment appointment) {
        String clinicEmail = "doctorbooking80@gmail.com"; // должен быть реальный (также прописанный в пропертис) адрес праксиса

        TimeSlot timeSlot = appointment.getTimeSlot();
        User user = appointment.getUser();

        String userSubject = "Terminbestätigung"; //Подтверждение записи на приём
        String userMessage = String.format("Sehr geehrte(r) Frau/Herr %s, Ihr Termin bei Dr. %s am %s ist bestätigt.",
                user.getSurName(), timeSlot.getDoctor().getLastName(), timeSlot.getDateTime().toString()); //Уважаемый(ая) %s, ваша запись к доктору %s на %s подтверждена

        String clinicSubject = "Neuer Termin"; //Новая запись на приём
        String clinicMessage = String.format("Herr/Frau %s ist für einen Termin bei Dr. %s am %s eingetragen.",
                user.getSurName(), timeSlot.getDoctor().getLastName(), timeSlot.getDateTime()); //тут без .toString(), потому что IDEA делает его серым
                                             //Господин(жа) %s записан(а) на приём к доктору %s на %s

        mailService.sendEmailAboutAppointment(user.getEmail(), userSubject, userMessage);
        mailService.sendEmailAboutAppointment(clinicEmail, clinicSubject, clinicMessage);


    }


// ПОЛУЧЕНИЕ ПОЛЬЗОВАТЕЛЕМ СПИСКА ВСЕХ СВОИХ ЗАПИСЕЙ НА ПРИЕМ К ВРАЧУ
    @PreAuthorize("hasRole('PATIENT')")
    public List<Appointment> getAppointmentsByUser(String Email) {
        User user = userRepository.findByEmail(Email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer nicht gefunden.")); //Пользователь не найден
        return appointmentRepository.findByUserId(user.getId());
    }


    // ПАЦИЕНТ ОТМЕНЯЕТ ЗАПИСЬ НА ПРИЕМ
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    @Transactional
    public void cancelAppointment(Long appointmentId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer nicht gefunden.")); //Пользователь не найден

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Der angeforderte Termin wurde nicht gefunden.")); //Запрашиваемая запись не найдена

        if (!appointment.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Sie können diesen Termin nicht stornieren."); //Вы не можете отменить эту запись
        }

        TimeSlot timeSlot = appointment.getTimeSlot();
        if (timeSlot == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Der angeforderte Terminzeitraum (Time-Slot) wurde nicht gefunden."); // Запрашиваемые дата и время приема (тайм-слот) не найдены
        }
        // ПРОВЕРКА НА ВРЕМЯ
        LocalDateTime now = LocalDateTime.now();
        if (timeSlot.getDateTime().isBefore(now.plusHours(24))) {
            throw new IllegalStateException("Es sind weniger als 24 Stunden bis zu Ihrem Termin beim Arzt. Daher können Sie den Termin nicht mehr stornieren.");
        }                                       //До приема у доктора осталось меньше 24 часов. Поэтому Вы уже не можете отменить запись

        // ОБНОВЛЯЕМ СТАТУС APPOINTMENT-а на "CANCELED"
        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);

        // ТАЙМ-СЛОТ СТАНОВИТСЯ ДОСТУПНЫМ ДЛЯ ДРУГИХ ПОЛЬЗОВАТЕЛЕЙ
        timeSlot.setIsBooked(false);
        timeSlotRepository.save(timeSlot);

        // ОТПРАВКА ПИСЕМ ОБ ОТМЕНЕ ЗАПИСИ НА ПРИЕМ
        String userSubject = "Terminabsage"; //Отмена записи на приём
        String userMessage = String.format("Sehr geehrte(r) Frau/Herr %s, Ihr Termin bei Dr. %s am %s wurde storniert.",
                user.getSurName(), timeSlot.getDoctor().getLastName(), timeSlot.getDateTime().toString()); //Уважаемый(ая) %s, ваша запись к доктору %s на %s была отменена
        mailService.sendEmailAboutAppointment(user.getEmail(), userSubject, userMessage);


        String clinicEmail = "doctorbooking80@gmail.com"; // должен быть реальный (также прописанный в пропертис) адрес праксиса
        String clinicSubject = "Terminabsage"; // Отмена записи на приём
        String clinicMessage = String.format("Herr/Frau %s hat den Termin bei Dr. %s am %s storniert.",
                user.getSurName(), timeSlot.getDoctor().getLastName(), timeSlot.getDateTime().toString()); //Господин(жа) %s отменил(а) запись к доктору %s на %s
        mailService.sendEmailAboutAppointment(clinicEmail, clinicSubject, clinicMessage);
    }

}











