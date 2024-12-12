package com.mvp.mvp_translation_project.utils;

import com.mvp.mvp_translation_project.models.Project;
import com.mvp.mvp_translation_project.models.ProjectPayment;
import com.mvp.mvp_translation_project.models.User;
import com.mvp.mvp_translation_project.models.dto.ProjectCreationDTO;
import com.mvp.mvp_translation_project.models.dto.ProjectDto;
import com.mvp.mvp_translation_project.models.dto.UserDto;
import com.mvp.mvp_translation_project.models.dto.UserRequestDto;
import com.mvp.mvp_translation_project.types.RoleType;
import com.mvp.mvp_translation_project.types.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class MapperUtils {

    @Autowired
    private static PasswordEncoder passwordEncoder;

    public static UserDto mapToDto(User user) {

        UserDto userDto = new UserDto();

        // Mapeo de la entidad al DTO
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setCellphone(user.getCellphone());
        userDto.setIdentityNumber(user.getIdentityNumber());
        userDto.setBirthDate(user.getBirthDate());

        return userDto;
    }

    public static User mapRequestToUser(UserRequestDto userRequestDto, RoleType roleType) {

        User user = new User();

        // Mapeo del DTO a la entidad
        user.setName(userRequestDto.getName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setBirthDate(userRequestDto.getBirthDate());
        user.setIdentityNumber(userRequestDto.getIdentityNumber());
        user.setCellphone(userRequestDto.getCellphone());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRole(roleType);
        user.setActive(true); // Activa el usuario por defecto

        return user;
    }

    public static ProjectDto mapProjectToDto(Project p) {
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

    public static Project mapToProject(ProjectCreationDTO projectCreationDTO) {
        Project project = new Project();

        project.setProjectPayment(projectCreationDTO.getProjectPayment());
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
}
