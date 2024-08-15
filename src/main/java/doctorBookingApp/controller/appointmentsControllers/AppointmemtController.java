package doctorBookingApp.controller.appointmentsControllers;

import doctorBookingApp.entity.Appointment;
import doctorBookingApp.service.bookingServices.AppointmentService;
import doctorBookingApp.service.userServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmemtController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;


    // ПОЛУЧЕНИЕ ПОЛЬЗОВАТЕЛЕМ СПИСКА ВСЕХ СВОИХ ЗАПИСЕЙ НА ПРИЕМ К ВРАЧУ

    @GetMapping("/my")
    public List<Appointment> getUserAppointments(Authentication authentication) {
        // Получаем UserDetails, который содержит информацию о текущем пользователе
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Используем email пользователя для поиска его записей на приём
        String email = userDetails.getUsername(); // т.к. у нас email используется как логин (username) для входа в систему
        return appointmentService.getAppointmentsByUser(email);
    }

    // ПАЦИЕНТ ОТМЕНЯЕТ ЗАПИСЬ НА ПРИЕМ

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
        appointmentService.cancelAppointment(id, email);

        return ResponseEntity.ok().body("Запись успешно отменена.");
    }


}
