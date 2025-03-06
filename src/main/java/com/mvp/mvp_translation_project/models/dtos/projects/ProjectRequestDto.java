package com.mvp.mvp_translation_project.models.dtos.projects;

import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.models.ProjectPayment;
import com.mvp.mvp_translation_project.types.TaskType;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@ToString

public class ProjectRequestDto {

    private String name;
    private String description;
    private LocalDateTime deadline;
    private String filePath;
    private TaskType taskType;
    private LanguagePair languagePair;
    private ProjectPayment projectPayment;
}
