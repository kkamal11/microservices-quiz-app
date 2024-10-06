package com.kamal.quiz_service.model;

import lombok.Data;

@Data
public class QuizDto {
    private String category;
    private Integer numOfQuestions;
    private String questionTitle;
}
