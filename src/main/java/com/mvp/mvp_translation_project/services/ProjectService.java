package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.events.ProjectCreatedEvent;
import com.mvp.mvp_translation_project.exceptions.DataAccessRuntimeException;
import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.models.Project;
import com.mvp.mvp_translation_project.models.User;
import com.mvp.mvp_translation_project.models.dto.ProjectCreationDTO;
import com.mvp.mvp_translation_project.models.dto.ProjectDto;
import com.mvp.mvp_translation_project.models.dto.UserDto;
import com.mvp.mvp_translation_project.repositories.LanguagePairRepository;
import com.mvp.mvp_translation_project.repositories.ProjectRepository;
import com.mvp.mvp_translation_project.types.StatusType;
import com.mvp.mvp_translation_project.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final LanguagePairRepository languagePairRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, LanguagePairRepository languagePairRepository, ApplicationEventPublisher eventPublisher) {
        this.projectRepository = projectRepository;
        this.languagePairRepository = languagePairRepository;
        this.eventPublisher = eventPublisher;
    }


    // Buscar o crear un LanguagePair único
    private LanguagePair findOrCreateLanguagePair(LanguagePair inputPair) {
        if (inputPair.getSourceLanguage().equals(inputPair.getTargetLanguage())) {
            System.out.println("Los idiomas no pueden ser iguales");
            return null;
        }

        return languagePairRepository.findBySourceLanguageAndTargetLanguage(
                inputPair.getSourceLanguage(),
                inputPair.getTargetLanguage()
        ).orElseGet(() -> {
            LanguagePair newPair = new LanguagePair();
            newPair.setSourceLanguage(inputPair.getSourceLanguage());
            newPair.setTargetLanguage(inputPair.getTargetLanguage());
            return languagePairRepository.save(newPair);
        });
    }

    // Validar los datos del proyecto antes de guardarlo
    private void validateProject(ProjectCreationDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Project name is required.");
        }
        if (dto.getDeadline() != null && dto.getDeadline().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Deadline must be a future date.");
        }
        if (dto.getLanguagePair() == null) {
            throw new IllegalArgumentException("Language pair is required.");
        }
    }

    // Crear un nuevo proyecto
    public ProjectDto saveProject(ProjectCreationDTO projectCreationDTO) {
        validateProject(projectCreationDTO);
        projectCreationDTO.setLanguagePair(findOrCreateLanguagePair(projectCreationDTO.getLanguagePair()));
        Project project = MapperUtils.mapToProject(projectCreationDTO);

        try {
            projectRepository.save(project);
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("Failed to save the project", e);
        }

        eventPublisher.publishEvent(new ProjectCreatedEvent(project));
        return MapperUtils.mapProjectToDto(project);
    }

    // Otros métodos de búsqueda y CRUD
    public List<ProjectDto> findProjects() {
        try {
            return projectRepository.findAll().stream()
                    .map(MapperUtils::mapProjectToDto).toList();
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("Failed to retrieve project list", e);
        }
    }

    public Project updateProject(Long id, Project p) {
        Project existingProject = projectRepository.findById(id).orElse(null);

        if (existingProject != null) {
            existingProject.setName(p.getName());
            existingProject.setDescription(p.getDescription());
            existingProject.setStartingDate(p.getStartingDate());
            existingProject.setDeadline(p.getDeadline());
            existingProject.setFinishedDate(p.getFinishedDate());
            existingProject.setStatus(p.getStatus());
            existingProject.setFilePath(p.getFilePath());
            existingProject.setTranslator(p.getTranslator());
            existingProject.setLanguagePair(findOrCreateLanguagePair(p.getLanguagePair()));

            try {
                return projectRepository.save(existingProject);
            } catch (DataAccessException e) {
                throw new DataAccessRuntimeException("Failed to update the project", e);
            }
        }
        return null;
    }

    public void deleteProject(Long id) {
        try {
            projectRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("Failed to delete the project", e);
        }
    }

    public Project findProjectById(Long id) {
        try {
            return projectRepository.findById(id).orElse(null);
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("Failed to find the project", e);
        }
    }

    public List<Project> findProjectsByStatus(StatusType status) {
        return projectRepository.findByStatus(status);
    }

    public List<Project> findProjectsByName(String name) {
        return projectRepository.findByName(name);
    }

    // Métodos adicionales para búsquedas específicas...


    public List<Project> findProjectsStartingAfter(LocalDateTime startingDate) {
        return projectRepository.findByStartingDateAfter(startingDate);
    }

    public List<Project> findProjectsByDeadlineBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return projectRepository.findByDeadlineBetween(startDate, endDate);
    }

    public List<Project> findProjectsByStatusAndStartingDateAfter(StatusType status, LocalDateTime startingDate) {
        return projectRepository.findByStatusAndStartingDateAfter(status, startingDate);
    }

    public List<Project> findProjectsByNameAndStatus(String name, StatusType status) {
        return projectRepository.findByNameAndStatus(name, status);
    }

    public List<Project> findProjectsByTranslator(Long translatorId) {
        return projectRepository.findByTranslatorId(translatorId);
    }

    public List<Project> findProjectsByLanguagePair(LanguagePair languagePair) {
        return projectRepository.findByLanguagePair(languagePair);
    }

    public List<Project> findProjectsFinished() {
        return projectRepository.findByFinishedDateIsNotNull();
    }

    public List<Project> findProjectsByNameContaining(String keyword) {
        return projectRepository.findByNameContaining(keyword);
    }

    public List<Project> findProjectsByDescriptionContaining(String keyword) {
        return projectRepository.findByDescriptionContaining(keyword);
    }

    public List<Project> findProjectsByFinishedDate(LocalDateTime finishedDate) {
        return projectRepository.findByFinishedDate(finishedDate);
    }
}
