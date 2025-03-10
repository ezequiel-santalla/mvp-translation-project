package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.exceptions.EmailSendingException;
import com.mvp.mvp_translation_project.models.Project;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private static final String ENCODING = "utf-8";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTestEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(getMailDirection());
        message.setSubject("Test Email");
        message.setText("This is a test email from " + getMailDirection());
        mailSender.send(message);
    }

    @Async
    public void sendSimpleMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(getMailDirection());
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Async
    public void sendPreRegisterEmail(String toEmail, String preRegisterToken) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, ENCODING);
            helper.setTo(toEmail);
            helper.setFrom(getMailDirection());
            helper.setSubject("Pre-registration Token");
            String emailContent = "<p>Enter your email and the token and complete your registration details.</p>"
                    + "<p>The token is valid for 48 hours.</p>"
                    + "<p>Token: <strong>" + preRegisterToken + "</strong></p>";
            helper.setText(emailContent, true);
        } catch (MessagingException e) {
            throw new EmailSendingException(toEmail, "Pre-registration User", e);
        }
        mailSender.send(message);
    }

    @Async
    public void sendRecoveryEmail(String toEmail, String recoveryToken) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, false, ENCODING);
            helper.setTo(toEmail);
            helper.setFrom(getMailDirection());
            helper.setSubject("Recovery Token");

            String recoveryLink = "link=" + recoveryToken;
            String emailContent = "<p>Go to the following link and enter the token, then change your password:</p>"
                    + "<p><a href=\"" + recoveryLink + "\">" + recoveryLink + "</a></p>"
                    + "<p>Token: " + recoveryToken + "</p>";

            helper.setText(emailContent, true);
        } catch (MessagingException e) {
            throw new EmailSendingException(toEmail, "Account Recovery", e);
        }
        mailSender.send(message);
    }

    @Async
    public void sendVerificationEmail(String toEmail, String verificationCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, ENCODING);
        helper.setTo(toEmail);
        helper.setFrom(getMailDirection());
        helper.setSubject("Código de verificación");
        helper.setText("Tu código de verificación es: " + verificationCode, true);
        mailSender.send(message);
    }

    @Async
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
            throw new EmailSendingException("Failed to send email with attachment", e);
        }
    }

    @Async
    public void sendProjectInvitation(List<String> translatorsEmails, Project project) {
        File attachment = new File(project.getFilePath());
        String subject = "Invitation to apply";
        String applyLink = "'link'";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // 'true' to enable multipart support
            for (String email : translatorsEmails) {
                helper.setTo(email);
                helper.setFrom(getMailDirection());
                helper.setSubject(subject);
                String emailContent = "<p>" + project.getName() + "</p>"
                        + "<p>" + project.getDescription() + "</p>"
                        + "<p>Language pair: <strong>" + project.getLanguagePair().toString() + "</strong></p>"
                        + "<p>:Project Payment" + project.getProjectPayment() + "</p>"
                        + "<p>Task Type:" + project.getTaskType() + "</p>"
                        + "<p>Deadline:" + project.getDeadline() + "</p>"
                        + "<p> Click here to apply <a href=\"" + applyLink + "\">" + applyLink + "</a></p>";
                helper.setText(emailContent, true);

                if (attachment.exists() && attachment.isFile()) {
                    helper.addAttachment(StringUtils.cleanPath(attachment.getName()), attachment);
                }

                mailSender.send(message);
            }
        } catch (MessagingException e) {
            throw new EmailSendingException("Failed to send email with attachment", e);
        }
    }

    public String getMailDirection() {
        return "translator.app.test@gmail.com";
    }

    @Async
    public void sendEmailToTranslators(List<String> translatorsEmails, String subject, String body) {
        for (String email : translatorsEmails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setFrom(getMailDirection());
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        }
    }
}

