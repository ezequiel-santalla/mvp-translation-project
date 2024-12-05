package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.models.Project;
import com.mvp.mvp_translation_project.repositories.ProjectRepository;
import com.mvp.mvp_translation_project.types.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findProjects() {
        return projectRepository.findAll();
    }

    public Project saveProject(Project p) {
        return projectRepository.save(p);
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
            existingProject.setLanguagePair(p.getLanguagePair());

            return projectRepository.save(existingProject);
        }
        return null;
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public Project findProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public List<Project> findProjectsByStatus(StatusType status) {
        return projectRepository.findByStatus(status);
    }

    public List<Project> findProjectsByName(String name) {
        return projectRepository.findByName(name);
    }

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
