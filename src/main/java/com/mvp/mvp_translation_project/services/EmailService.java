package com.mvp.mvp_translation_project.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTestEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(getMailDirection());
        message.setSubject("Test Email");
        message.setText("This is a test email from "+getMailDirection());
        mailSender.send(message);
    }

    public void sendSimpleMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(getMailDirection());
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendVerificationEmail(String toEmail, String verificationCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        helper.setTo(toEmail);
        helper.setFrom(getMailDirection());
        helper.setSubject("Código de verificación");
        helper.setText("Tu código de verificación es: " + verificationCode, true);
        mailSender.send(message);
    }

    public void sendEmailWithAttachment(String to, String subject, String body, File attachment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // 'true' to enable multipart support

            helper.setTo(to);
            helper.setFrom(getMailDirection());
            helper.setSubject(subject);
            helper.setText(body, false);

            if (attachment != null && attachment.exists() && attachment.isFile()) {
                helper.addAttachment(StringUtils.cleanPath(attachment.getName()), attachment);
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with attachment", e);
        }
    }

    public String getMailDirection() {
        return "translator.app.test@gmail.com";
    }
}

