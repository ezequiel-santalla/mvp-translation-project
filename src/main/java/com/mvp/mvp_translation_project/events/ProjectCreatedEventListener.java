package com.mvp.mvp_translation_project.events;

import com.mvp.mvp_translation_project.models.Project;
import com.mvp.mvp_translation_project.models.User;
import com.mvp.mvp_translation_project.services.EmailService;
import com.mvp.mvp_translation_project.services.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectCreatedEventListener {

    private final EmailService emailService;
    private final UserService userService;

    public ProjectCreatedEventListener(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @EventListener
    public void onProjectCreated(ProjectCreatedEvent event) {
        Project project = event.project();

        // Obtener los correos de los traductores para el par de lenguajes
        List<String> translatorsEmails = userService.getUsersByLanguagePair(project.getLanguagePair())
                .stream()
                .map(User::getEmail)
                .toList();

        // Enviar el correo a los traductores
        emailService.sendProjectInvitation(translatorsEmails, project);
    }
}
