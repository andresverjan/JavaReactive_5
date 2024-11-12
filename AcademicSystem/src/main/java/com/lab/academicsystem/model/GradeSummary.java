package com.lab.academicsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeSummary {

    private String studentName;
    private String subjectName;
    private double gradeValue;
}
