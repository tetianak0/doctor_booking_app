package doctorBookingApp.service.userService;


import doctorBookingApp.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
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











// КОММЕНТАРИИ К КОДУ


//ОТПРАВКА НА ПОЧТУ ПРОСТО КОДА. Как вариант: пользователь может ввести его в регистрационную форму и тем самым подтвердить свою личность
//    public void sendConfirmationEmail(User user, String confirmationCode) {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom("doctorbooking4@gmail.com");
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setSubject("Registration Confirmation Code");
//        mailMessage.setText("Bitte bestätigen Sie Ihre Registrierung mit dem Code: " + confirmationCode);
//        mailSender.send(mailMessage);
//    }



//    public void sendConfirmationEmailWithHTML(User user) throws MessagingException {
//
//        String confirmationUrl = "http://localhost:8080/api/users/confirm?confirmationCode=" + user.getConfirmationCode();
//
//        /*        localhost:8080 - куда отправляем
//        api/users/confirm?confirmationCode= - путь        */
//
//        String subject = "Registration Confirmation Code";
//        String textBody = "<p> Click the link below to confirm email:</p>" +
//                "<a href=\"" + confirmationUrl + "\">Confirm Email </a>"; //размещаем ссылку
//
//        // обратный слэш (\) ставится для того, чтобы кавычки воспринимались как часть текста. А он воспринмается как спецкод
//
//        /*
//         для того чтобы в текст включить что-то отличное от обычного текста, в библиотеке mail существует два класса:
//         MimeMessage  и MimeMessageHelper
//         */
//
//        MimeMessage message = mailSender.createMimeMessage();
//
//
//        /*MimeMessage — это класс, который из Java Mail, и он используется, если нам нужно создать и отправить сообщение, которое будет содерживать различные форматы
//        контента (HTML, какие-то вложения, картинки и так далее).
//        Особенности:
//                     - многоформатность
//                     - может быть множество получателей (поддержка нескольких получателей с помощью там полей там CC, BCC и т.д.; копии, скрытые копии и так далее)
//                     - позволяет вручную настраивать, задавать и изменить заголовки сообщения, если она нужна.
//
//         */
//
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//        /* Helper это по сути помогатель. Для упрощения работы. То есть он позволяет легко устанавливать HTML-сообщения, добавлять вложения, изображения, так далее.
//         Поддерживает многобайтовые символы, то есть поддержку как бы в самом коде
//
//          */
//
//        helper.setTo(user.getEmail());
//        helper.setSubject(subject);
//        helper.setText(textBody, true);
//
//        mailSender.send(message);
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom("doctorbookingBoss@gmail.com");
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setSubject("Registration Confirmation Code");
//        mailMessage.setText("Please confirm your registration with code: " + user.getConfirmationCode());
//        mailSender.send(mailMessage);
//    }

}


