package com.kamal.quiz_service.controller;


import com.kamal.quiz_service.model.QuestionWrapper;
import com.kamal.quiz_service.model.QuizDto;
import com.kamal.quiz_service.model.UserResponse;
import com.kamal.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumOfQuestions(), quizDto.getQuestionTitle());
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer quizId){
        return quizService.getQuizQuestions(quizId);
    }

    @PostMapping("/submit/{quizId}")
    public ResponseEntity<String> submitQuiz(@PathVariable Integer quizId, @RequestBody List<UserResponse> responses){
        return quizService.calculateScore(quizId, responses);
    }

}
