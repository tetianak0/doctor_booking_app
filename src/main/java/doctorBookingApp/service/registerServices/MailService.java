package doctorBookingApp.service.registerServices;


import doctorBookingApp.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage; //для замутированного метода
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class MailService {
    private final JavaMailSender mailSender;

    // ФОРМИРУЕТ И ОТПРАВЛЯЕТ ПИСЬМО С КОДОМ ПОДТВЕРЖДЕНИЯ ПРИ РЕГИСТРАЦИИ И ВОССТАНОВЛЕНИИ ПАРОЛЯ

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


    // ФОРМИРУЕТ И ОТПРАВЛЯЕТ ПИСЬМО ПАЦИЕНТУ И В ПРАКСИС С ИНФОРМАЦИЕЙ О ЗАБРОНИРОВАННОМ ПРИЕМЕ
    // (можем использовать для отправки писем любого содержания)

    public void sendEmailAboutAppointment(String recipientEmail, String subject, String textBody) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("doctorbooking80@gmail.com");
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(textBody);
        mailSender.send(mailMessage);
    }


}


