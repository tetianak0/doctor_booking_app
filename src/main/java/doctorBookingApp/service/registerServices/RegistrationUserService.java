package doctorBookingApp.service.registerServices;

import doctorBookingApp.dto.usersDTO.NewUserDTO;
import doctorBookingApp.dto.usersDTO.UserDTO;
import doctorBookingApp.entity.ConfirmationCode;
import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.Role;
import doctorBookingApp.entity.enums.State;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.repository.ConfirmationCodeRepository;
import doctorBookingApp.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RegistrationUserService {

    private final UserRepository userRepository;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final ConfirmationCodeService confirmationCodeService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;



// создает нового пользователя, сохраняет его, генерирует и сохраняет код подтверждения
// (метод для этого в ConfirmationCodeService),
// а затем отправляет код подтверждения на почту пользователя.

    @Transactional
    public UserDTO registrationUser(NewUserDTO newUser) throws RestException, MessagingException {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new RestException(HttpStatus.CONFLICT, "Пользователь с таким email уже существует: " + newUser.getEmail());
        }

        User user = User.builder()
                .firstname(newUser.getFirstname())
                .surName(newUser.getSurName())
                .birthDate(newUser.getBirthDate())
                .phoneNumber(newUser.getPhoneNumber())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .role(Role.PATIENT)
                .state(State.NOT_CONFIRMED)
                .build();

        userRepository.save(user);

        String codeValue = confirmationCodeService.createAndSaveConfirmationCode(user);

        mailService.sendConfirmationEmailWithHTML(user, codeValue);// отправка письма


        return UserDTO.from(user);

    }

    //ИЩЕМ КОД ПОДТВЕРЖДЕНИЯ, МЕНЯЕМ состояние пользователя на CONFIRMED, СОХРАНЯЕМ его.

    @Transactional
    public void confirmUser(String confirmationCode) throws RestException {
        ConfirmationCode code = confirmationCodeRepository
                .findByCodeAndExpiredDateTimeAfter(confirmationCode, LocalDateTime.now())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Код не найден или срок его действия истек"));

        User user = code.getUser();
        user.setState(State.CONFIRMED);
        userRepository.save(user);
    }




}
