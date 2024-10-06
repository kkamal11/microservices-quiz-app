package com.kamal.question_service.service;

import com.kamal.question_service.dao.QuestionDao;
import com.kamal.question_service.model.Question;
import com.kamal.question_service.model.QuestionWrapper;
import com.kamal.question_service.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;


    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>( "Question Added", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>( "Error While Adding Question", HttpStatus.BAD_REQUEST);
    }

    public String deleteQuestion(Question question) {
        questionDao.delete(question);
        return "Deleted";
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int numQ) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer qId : questionIds){
            Optional<Question> q = questionDao.findById(qId);
            questions.add(q.get());
        }

        for(Question question: questions){
            QuestionWrapper qw = new QuestionWrapper(
                    question.getId(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4(), question.getQuestionTitle()
            );
            wrappers.add(qw);
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);

    }

    public ResponseEntity<Integer> getScore(List<UserResponse> responses) {
        int score = 0;
        for(UserResponse response : responses){
            Optional<Question> question = questionDao.findById(response.getId());
            if(question.get().getQuestionAnswer().equals(response.getResponse())){
                score++;
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
