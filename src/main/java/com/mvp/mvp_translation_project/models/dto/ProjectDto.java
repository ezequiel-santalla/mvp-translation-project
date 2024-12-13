package com.mvp.mvp_translation_project.models.dto;

import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.models.ProjectPayment;
import com.mvp.mvp_translation_project.types.PaymentType;
import com.mvp.mvp_translation_project.types.StatusType;
import com.mvp.mvp_translation_project.types.TaskType;
import lombok.*;

import java.math.BigDecimal;
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
