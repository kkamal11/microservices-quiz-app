package com.kamal.quiz_service.service;

import com.kamal.quiz_service.dao.QuizDao;
import com.kamal.quiz_service.feign.QuizInterface;
import com.kamal.quiz_service.model.QuestionWrapper;
import com.kamal.quiz_service.model.Quiz;
import com.kamal.quiz_service.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category,Integer numOfQuestions, String questionTitle) {

        List<Integer> questionList = quizInterface.getQuestionsForQuiz(category, numOfQuestions).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(questionTitle);
        quiz.setQuestionIds(questionList);

        quizDao.save(quiz);

        return new ResponseEntity<>("Success : Quiz Saved", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<Integer> questionIdList = quiz.get().getQuestionIds();

        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromID(questionIdList);
        return questions;
    }

    public ResponseEntity<String> calculateScore(Integer quizId, List<UserResponse> responses) {

        Integer score = quizInterface.getScore(responses).getBody();
        return new ResponseEntity<>("Your score is : " + score , HttpStatus.OK);
    }
}
