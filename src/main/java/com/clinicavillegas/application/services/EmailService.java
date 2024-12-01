package com.clinicavillegas.application.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.EmailRequest;
import com.clinicavillegas.application.models.Cita;

import jakarta.mail.MessagingException;
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

    public void enviarRecordatorio(Cita cita) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            helper.setFrom("ClinicaDentalVillegas<" + emisor + ">");
            helper.setTo(cita.getUsuario().getCorreo());
            helper.setSubject("Recordatorio de cita");
    
            // Contenido HTML con diseño mejorado y más detalles
            String htmlContent = "<!DOCTYPE html>"
                    + "<html lang='es'>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                    + "<title>Recordatorio de cita</title>"
                    + "</head>"
                    + "<body style='font-family: Arial, sans-serif; color: #333; margin: 0; padding: 0;'>"
                    + "<div style='max-width: 600px; margin: 20px auto; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden;'>"
                    + "<header style='background-color: #0F2650; color: white; text-align: center; padding: 10px 0;'>"
                    + "<h1 style='margin: 0;'>Clínica Dental Villegas</h1>"
                    + "</header>"
                    + "<section style='padding: 20px;'>"
                    + "<h2 style='color: #0F2650;'>Estimado(a) " + cita.getUsuario().getNombres() + " " 
                    + cita.getUsuario().getApellidoPaterno() + " " + cita.getUsuario().getApellidoMaterno() + "</h2>"
                    + "<p>Gracias por confiar en <strong>Clínica Dental Villegas</strong>. A continuación, te recordamos los detalles de tu cita:</p>"
                    + "<table style='width: 100%; border-collapse: collapse; margin: 20px 0;'>"
                    + "<tr style='background-color: #f9f9f9;'>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>Fecha de la cita:</td>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>" + cita.getFecha().toString() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>Hora:</td>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>" + cita.getHora().toString() + "</td>"
                    + "</tr>"
                    + "<tr style='background-color: #f9f9f9;'>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>Tratamiento:</td>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>" + cita.getTratamiento().getNombre() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>Monto:</td>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>S/ " + cita.getMonto().toString() + "</td>"
                    + "</tr>"
                    + "<tr style='background-color: #f9f9f9;'>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>Documento:</td>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>" 
                    + cita.getTipoDocumento().getNombre() + " - " + cita.getNumeroIdentidad() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>Dentista:</td>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>Dr. " + cita.getDentista().getUsuario().getNombres() + " " 
                    + cita.getDentista().getUsuario().getApellidoPaterno() + " " + cita.getDentista().getUsuario().getApellidoMaterno() + "</td>"
                    + "</tr>"
                    + "</table>"
                    + "<p style='font-size: 0.9em;'>Por favor, llega 10 minutos antes de la hora programada.</p>"
                    + "</section>"
                    + "<footer style='background-color: #f4f4f4; color: #666; text-align: center; padding: 10px;'>"
                    + "<p style='margin: 0;'>© 2024 Clínica Dental Villegas. Todos los derechos reservados.</p>"
                    + "</footer>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
    
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
    
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
}
