package doctorBookingApp.service;

import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.Role;
import doctorBookingApp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


//этот класс по сути выполняет сервисную функцию — инициализирует данные (создает администратора) при запуске приложения.
@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByRole(Role.ADMIN).isEmpty()) {
            User admin = new User();
            admin.setEmail("doctorbooking80@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin_password"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }


}
