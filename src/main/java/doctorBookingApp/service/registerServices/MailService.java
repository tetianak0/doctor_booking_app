package doctorBookingApp.service.registerServices;


import doctorBookingApp.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
//import org.springframework.mail.SimpleMailMessage; //для замутированного метода
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//формирует и отправляет письмо с кодом подтверждения
public class MailService {
    private final JavaMailSender mailSender;

    public void sendConfirmationEmailWithHTML(User user, String confirmationCode) throws MessagingException {

        String confirmationUrl = "http://localhost:8080/api/users/confirm?confirmationCode=" + confirmationCode;

        String subject = "Registration Confirmation Code";
        String textBody = "<p> Click the link below to confirm email:</p>" +
                "<a href=\"" + confirmationUrl + "\">Confirm Email </a>";

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(textBody, true);

        mailSender.send(message);


    }








//ОТПРАВКА НА ПОЧТУ ПРОСТО КОДА. Как вариант: пользователь может ввести его в регистрационную форму и тем самым подтвердить свою личность
//    public void sendConfirmationEmail(User user, String confirmationCode) {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom("doctorbooking4@gmail.com");
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setSubject("Registration Confirmation Code");
//        mailMessage.setText("Bitte bestätigen Sie Ihre Registrierung mit dem Code: " + confirmationCode);
//        mailSender.send(mailMessage);
//    }



}


