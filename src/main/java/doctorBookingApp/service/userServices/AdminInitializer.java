package doctorBookingApp.service.userServices;

import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.State;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//import java.time.LocalDate;


//этот класс по сути выполняет сервисную функцию — инициализирует данные (создает администратора) при запуске приложения.
@Component
@AllArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserService userService;


    @Override
    public void run(String... args) throws Exception {
        if (!userService.existsByEmail("doctorbooking80@gmail.com")) {
            User admin = new User();
            admin.setEmail("doctorbooking80@gmail.com");
            admin.setPassword("admin_password");
            admin.setFirstname("ADMIN");
            admin.setSurName("Adminovich");
            admin.setBirthDate("1980-01-01");
            //admin.setBirthDate(LocalDate.of(1980, 01, 01)); c этим я позже заморочусь - много тянет за собой. Но это хорошая тема для валидации данных
            admin.setPhoneNumber("+4917234567890");
            admin.setState(State.CONFIRMED);

            userService.saveAdmin(admin);

        }
    }
}
