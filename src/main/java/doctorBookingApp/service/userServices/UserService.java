package doctorBookingApp.service.userServices;


import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.Role;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.repository.userRepositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


//МЕТОД ДЛЯ СОХРАНЕНИЯ АДМИНА для более гибкого управления, т.к. в дальнейшем планируется установить контроль замены пароля при первом входе
    public User saveAdmin(User admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword())); //хешируем пароль админа
        admin.setRole(Role.ADMIN); // устанавливаем роль администратора
        //admin.setFirstLogin(true); // устанавливаем флаг для принудительной смены пароля
        return userRepository.save(admin);
    }

    //ЭТО НА БУДУЩЕЕ - К ТЕМЕ ПРИНУДИТЕЛЬНОЙ ЗАМЕНЫ ПАРОЛЯ ДЛЯ АДМИНА
//    public void handleFirstLogin(User user, String newPassword) {
//        if (user.isFirstLogin()) {
//            user.setPassword(passwordEncoder.encode(newPassword));
//            user.setFirstLogin(false); // Сбрасываем флаг после смены пароля
//            userRepository.save(user);
//        }
//    }



    // ПОЛУЧЕНИЕ ПОЛЬЗОВАТЕЛЯ
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUserById(Long userId) throws RestException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден."));
        return UserDTO.from(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUserByEmail(String email) throws RestException {
        return UserDTO.from(userRepository.findByEmail(email)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с email " + email + " не найден")));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUserByPhoneNumber(String phoneNumber) throws RestException {
        return UserDTO.from(userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с номером телефона " + phoneNumber + " не найден.")));
    }



    // РЕДАКТИРОВАНИЕ ДАННЫХ ПОЛЬЗОВАТЕЛЯ

    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    @Transactional
    public UserDTO editUser(Long userId, UserDTO userDTO) throws RestException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден."));
        user.setSurName(userDTO.getSurName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
        return UserDTO.from(user);

    }


    // УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteUserById(Long userId) throws RestException {
        if (!userRepository.existsById(userId)) {
            throw new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден.");
        }
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    @Transactional
    public void deleteUserByEmail(String email) throws RestException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с Email " + email + " не найден."));
        userRepository.deleteByEmail(user.getEmail());

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteUserByPhoneNumber(String phoneNumber) throws RestException {
        User user = userRepository.findByEmail(phoneNumber)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с Email " + phoneNumber + " не найден."));
        userRepository.deleteByPhoneNumber(user.getPhoneNumber());

    }


}



