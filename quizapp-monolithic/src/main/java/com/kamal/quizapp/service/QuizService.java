package com.kamal.quizapp.service;

import com.kamal.quizapp.dao.QuestionDao;
import com.kamal.quizapp.dao.QuizDao;
import com.kamal.quizapp.model.Question;
import com.kamal.quizapp.model.QuestionWrapper;
import com.kamal.quizapp.model.Quiz;
import com.kamal.quizapp.model.UserResponse;
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
    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questionList = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionList);

        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<Question> questionsFromDB = quiz.get().getQuestions();

        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for(Question q : questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(
                    q.getId(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4(), q.getQuestionTitle()
            );
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<String> calculateScore(Integer quizId, List<UserResponse> responses) {
        Quiz quiz = quizDao.findById(quizId).get();
        List<Question> questions = quiz.getQuestions();

        int score = 0;
        int i = 0;
        for(UserResponse response : responses){
            if(response.getResponse().equals(questions.get(i).getQuestionAnswer())){
                score++;
            }
            i++;
        }
        return new ResponseEntity<>("Your score is : " + score + "/" + i, HttpStatus.OK);
    }
}
