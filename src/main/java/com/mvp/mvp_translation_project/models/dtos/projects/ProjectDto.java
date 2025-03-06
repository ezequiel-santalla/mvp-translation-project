package com.mvp.mvp_translation_project.models.dtos.projects;

import com.mvp.mvp_translation_project.models.ProjectPayment;
import com.mvp.mvp_translation_project.models.dtos.languages.LanguagePairDto;
import com.mvp.mvp_translation_project.models.dtos.users.UserDto;
import com.mvp.mvp_translation_project.types.StatusType;
import com.mvp.mvp_translation_project.types.TaskType;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@ToString

public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private UserDto translator;
    private LocalDateTime startingDate;
    private LocalDateTime deadline;
    private LocalDateTime finishedDate;
    private String filePath;
    private TaskType taskType;
    private LanguagePairDto languagePair;
    private ProjectPayment projectPayment;
    private StatusType status;
}
