package doctorBookingApp.service.authenticationServices;

import doctorBookingApp.entity.User;
import doctorBookingApp.repository.userRepositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;



    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    public void sendResetPasswordLink(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
               .orElseThrow(() -> new RuntimeException("Ein Benutzer mit dieser E-Mail-Adresse wurde nicht gefunden.")); //Пользователь с таким email не найден

        String tokenForPassword = UUID.randomUUID().toString();
        user.setTokenForResetPassword(tokenForPassword);
        userRepository.save(user);

        String resetURL = "http://localhost:5173/reset-password?token=" + tokenForPassword;

        String subjectOfLetter = "Password reset";
        String textBody = "<p> Click the link below to reset password:</p>" +
                "<a href=\"" + resetURL + "\"> Email for new password </a>";

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(user.getEmail());
        helper.setSubject(subjectOfLetter);
        helper.setText(textBody, true);

        mailSender.send(message);

    }



    public boolean validateResetPasswordToken(String tokenForPassword) {
        return userRepository.findByTokenForResetPassword(tokenForPassword).isPresent();
    }


    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    public boolean updatePassword(String tokenForResetPassword, String newPassword) {

        User user = userRepository.findByTokenForResetPassword(tokenForResetPassword)
                .orElseThrow(() -> new RuntimeException("Token is wrong"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setTokenForResetPassword(null);
        userRepository.save(user);

        return true;
    }

}

























