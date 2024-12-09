package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.models.Project;
import com.mvp.mvp_translation_project.services.ProjectService;
import com.mvp.mvp_translation_project.types.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Obtener todos los proyectos
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.findProjects();
    }

    // Crear un proyecto
    @PostMapping
    public Project postProject(@RequestBody Project project) {
        project.setStartingDate(LocalDateTime.from(LocalDateTime.now()));

        project.setStatus(StatusType.PENDING);
        return projectService.saveProject(project);
    }

    // Actualizar un proyecto dado su ID
    @PutMapping("/{id}")
    public Project putProject(@PathVariable Long id, @RequestBody Project project) {
        return projectService.updateProject(id, project);
    }

    // Eliminar un proyecto por ID
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    // Obtener un proyecto por ID
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.findProjectById(id);
    }

    // Buscar proyectos por estado
    @GetMapping("/status")
    public List<Project> getProjectsByStatus(@RequestParam StatusType status) {
        return projectService.findProjectsByStatus(status);
    }

    // Buscar proyectos por nombre
    @GetMapping("/name")
    public List<Project> getProjectsByName(@RequestParam String name) {
        return projectService.findProjectsByName(name);
    }

    // Buscar proyectos que empiezan después de una fecha
    @GetMapping("/starting-date/after")
    public List<Project> getProjectsStartingAfter(@RequestParam LocalDateTime startingDate) {
        return projectService.findProjectsStartingAfter(startingDate);
    }

    // Buscar proyectos por rango de fechas de vencimiento
    @GetMapping("/deadline")
    public List<Project> getProjectsByDeadlineBetween(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return projectService.findProjectsByDeadlineBetween(startDate, endDate);
    }

    // Buscar proyectos por estado y fecha de inicio
    @GetMapping("/status/starting-date/after")
    public List<Project> getProjectsByStatusAndStartingDate(
            @RequestParam StatusType status, @RequestParam LocalDateTime startingDate) {
        return projectService.findProjectsByStatusAndStartingDateAfter(status, startingDate);
    }

    // Buscar proyectos por nombre y estado
    @GetMapping("/name/status")
    public List<Project> getProjectsByNameAndStatus(
            @RequestParam String name, @RequestParam StatusType status) {
        return projectService.findProjectsByNameAndStatus(name, status);
    }

    // Buscar proyectos por traductor
    @GetMapping("/translator")
    public List<Project> getProjectsByTranslator(@RequestParam Long translatorId) {
        return projectService.findProjectsByTranslator(translatorId);
    }

    // Buscar proyectos por par de idiomas
    @GetMapping("/language-pair")
    public List<Project> getProjectsByLanguagePair(@RequestParam LanguagePair languagePair) {
        return projectService.findProjectsByLanguagePair(languagePair);
    }

    // Buscar proyectos finalizados
    @GetMapping("/finished")
    public List<Project> getFinishedProjects() {
        return projectService.findProjectsFinished();
    }

    // Buscar proyectos por nombre que contengan una palabra clave
    @GetMapping("/name/containing")
    public List<Project> getProjectsByNameContaining(@RequestParam String keyword) {
        return projectService.findProjectsByNameContaining(keyword);
    }

    // Buscar proyectos por descripción que contengan una palabra clave
    @GetMapping("/description/containing")
    public List<Project> getProjectsByDescriptionContaining(@RequestParam String keyword) {
        return projectService.findProjectsByDescriptionContaining(keyword);
    }

    // Buscar proyectos por estado y fecha de finalización
    @GetMapping("/finished-date")
    public List<Project> getProjectsByFinishedDate(@RequestParam LocalDateTime finishedDate) {
        return projectService.findProjectsByFinishedDate(finishedDate);
    }

}
