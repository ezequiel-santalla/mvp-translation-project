package com.mvp.mvp_translation_project.repositories;

import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.models.Project;
import com.mvp.mvp_translation_project.types.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByName(String name);

    List<Project> findByStatus(StatusType status);

    List<Project> findByStartingDateAfter(LocalDateTime startingDate);

    List<Project> findByDeadlineBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Project> findByStatusAndStartingDateAfter(StatusType status, LocalDateTime startingDate);

    List<Project> findByNameAndStatus(String name, StatusType status);

    List<Project> findByTranslatorId(Long translatorId);

    List<Project> findByLanguagePair(LanguagePair languagePair);

    List<Project> findByFinishedDateIsNotNull();

    List<Project> findByNameContaining(String keyword);

    List<Project> findByDescriptionContaining(String keyword);

    List<Project> findByFinishedDate(LocalDateTime finishedDate);
}
