package doctorBookingApp.service.userService;


import doctorBookingApp.entity.ConfirmationCode;
import doctorBookingApp.entity.User;
import doctorBookingApp.repository.ConfirmationCodeRepository;
import doctorBookingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConfirmationCodeService {

    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserRepository userRepository;

    public String createAndSaveConfirmationCode(User user) {
        String codeValue = UUID.randomUUID().toString();
        ConfirmationCode confirmationCode = ConfirmationCode.builder()
                .code(codeValue)
                .user(user)
                .expiredDateTime(LocalDateTime.now().plusHours(12))
                .build();
        confirmationCodeRepository.save(confirmationCode);
        return codeValue;
    }









}
