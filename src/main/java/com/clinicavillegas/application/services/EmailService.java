package com.clinicavillegas.application.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.EmailRequest;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emisor;

    public String sendEmail(EmailRequest email) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Clinica Dental Villegas<" + emisor + ">");
            mailMessage.setTo(email.getReceptor());
            mailMessage.setSubject(email.getAsunto());
            mailMessage.setText(email.getContenido());

            javaMailSender.send(mailMessage);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Email sending error!";
        }
    }

    public String sendCode(String email) {

        Random random = new Random();
        int code = random.nextInt(999999);
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("ClinicaDentalVillegas<" + emisor + ">");
            helper.setTo(email);
            helper.setSubject("Código de verificación para el registro");

            String htmlContent = "<!DOCTYPE html>"
                    + "<html lang='es'>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                    + "<title>Código de Verificación</title>"
                    + "</head>"
                    + "<body style='font-family: Arial, sans-serif; color: #333;'>"
                    + "<h2 style='color: #4CAF50;'>¡Hola!</h2>"
                    + "<p>Gracias por registrarte en Clínica Dental Villegas.</p>"
                    + "<p>Tu código de verificación es: <strong style='font-size: 1.2em;'>" + code + "</strong></p>"
                    + "<p>Introduce este código en la página de verificación para completar tu registro.</p>"
                    + "<br>"
                    + "<p>Atentamente,</p>"
                    + "<p><strong>Clínica Dental Villegas</strong></p>"
                    + "</body>"
                    + "</html>";

            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

            return String.valueOf(code);
        } catch (Exception e) {
            return "Error al enviar el código de verificación: " + e.getMessage();
        }
    }
}
