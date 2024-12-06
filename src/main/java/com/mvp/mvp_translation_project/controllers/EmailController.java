package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-mail")
    public String sendMail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        emailService.sendSimpleMail(to, subject, body);
        return "Email sent to " + to;
    }

    @GetMapping("/send-test-mail")
    public String sendMail(@RequestParam String to) {
        emailService.sendTestEmail(to);
        return "Email sent to " + to;
    }

    @GetMapping("/send-mail-file")
    public String sendMail(@RequestParam String to, @RequestParam String subject,
                           @RequestParam String body, @RequestParam String filePath) {
        File attachment = new File(filePath);
        emailService.sendEmailWithAttachment(to, subject, body, attachment);
        return "Email sent to " + to;
    }

}

