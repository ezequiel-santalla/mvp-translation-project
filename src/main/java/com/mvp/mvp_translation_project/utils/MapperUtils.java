package com.mvp.mvp_translation_project.utils;

import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.models.Project;
import com.mvp.mvp_translation_project.models.ProjectPayment;
import com.mvp.mvp_translation_project.models.User;
import com.mvp.mvp_translation_project.models.dto.*;
import com.mvp.mvp_translation_project.types.RoleType;
import com.mvp.mvp_translation_project.types.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class MapperUtils {

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
        user.setPassword(userRequestDto.getPassword());
        user.setRole(roleType);
        user.setActive(true); // Activa el usuario por defecto

        return user;
    }

    // Mapear de Project a ProjectDto
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
        projectDto.setLanguagePair(mapToLanguagePairDto(p.getLanguagePair()));
        projectDto.setProjectPayment(p.getProjectPayment());
        projectDto.setStatus(p.getStatus());

        return projectDto;
    }

    // Mapear de ProjectCreationDTO a Project
    public static Project mapToProject(ProjectCreationDTO projectCreationDTO) {
        Project project = new Project();

        // Buscar o crear el LanguagePair
        project.setLanguagePair(projectCreationDTO.getLanguagePair());

        project.setProjectPayment(projectCreationDTO.getProjectPayment());
        project.setName(projectCreationDTO.getName());
        project.setDescription(projectCreationDTO.getDescription());
        project.setDeadline(projectCreationDTO.getDeadline());
        project.setFilePath(projectCreationDTO.getFilePath());
        project.setStartingDate(LocalDateTime.now());
        project.setTaskType(projectCreationDTO.getTaskType());
        project.setStatus(StatusType.PENDING);

        return project;
    }

    public static LanguagePairDto mapToLanguagePairDto(LanguagePair languagePair){

        LanguagePairDto languagePairDto = new LanguagePairDto();

        languagePairDto.setSourceLanguage(languagePair.getSourceLanguage().toDto());
        languagePairDto.setTargetLanguage(languagePair.getTargetLanguage().toDto());

        return languagePairDto;
    }

}
