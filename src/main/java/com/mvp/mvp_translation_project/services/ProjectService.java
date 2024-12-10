package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.exceptions.DataAccessRuntimeException;
import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.models.Project;
import com.mvp.mvp_translation_project.models.ProjectPayment;
import com.mvp.mvp_translation_project.models.User;
import com.mvp.mvp_translation_project.models.dto.ProjectCreationDTO;
import com.mvp.mvp_translation_project.models.dto.ProjectDto;
import com.mvp.mvp_translation_project.models.dto.UserDto;
import com.mvp.mvp_translation_project.repositories.ProjectRepository;
import com.mvp.mvp_translation_project.types.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    private ProjectDto mapToDto(Project p) {
        ProjectDto projectDto = new ProjectDto();

        projectDto.setId(p.getId());
        projectDto.setName(p.getName());
        projectDto.setDescription(p.getDescription());
        projectDto.setTranslator(p.getTranslator() != null ? mapToDto(p.getTranslator()) : null);
        projectDto.setDeadline(p.getDeadline());
        projectDto.setFilePath(p.getFilePath());
        projectDto.setFinishedDate(p.getFinishedDate());
        projectDto.setStartingDate(p.getStartingDate());
        projectDto.setTaskType(p.getTaskType());
        projectDto.setLanguagePair(p.getLanguagePair());
        projectDto.setStatus(p.getStatus());

        return projectDto;
    }

    private Project mapToProject(ProjectCreationDTO projectCreationDTO) {
        Project project = new Project();
        ProjectPayment projectPayment = new ProjectPayment();

        projectPayment.setPaymentType(projectCreationDTO.getPaymentType());
        projectPayment.setFlatFee(projectCreationDTO.getFlatFee());
        projectPayment.setRate(projectCreationDTO.getRate());
        projectPayment.setQuantity(projectCreationDTO.getQuantity()); // Asegúrate de usar getQuantity()
        project.setProjectPayment(projectPayment);

        project.setName(projectCreationDTO.getName());
        project.setDescription(projectCreationDTO.getDescription());
        project.setDeadline(projectCreationDTO.getDeadline());
        project.setFilePath(projectCreationDTO.getFilePath());
        project.setStartingDate(LocalDateTime.now());
        project.setTaskType(projectCreationDTO.getTaskType());
        project.setLanguagePair(projectCreationDTO.getLanguagePair());
        project.setStatus(StatusType.PENDING);

        return project;
    }


    public List<ProjectDto> findProjects() {
        try {
            return projectRepository.findAll().stream()
                    .map(this::mapToDto).toList();
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("Failed to retrieve user list", e);
        }
    }

    public ProjectDto saveProject(ProjectCreationDTO projectCreationDTO) {
        Project project = mapToProject(projectCreationDTO);

        projectRepository.save(project);

        return mapToDto(project);
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

    private UserDto mapToDto(User user) {
        if (user == null) {
            return null; // O lanza una excepción personalizada, dependiendo de la lógica
        }

        UserDto userDto = new UserDto();

        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setCellphone(user.getCellphone());
        userDto.setIdentityNumber(user.getIdentityNumber());
        userDto.setBirthDate(user.getBirthDate());

        return userDto;
    }

}
