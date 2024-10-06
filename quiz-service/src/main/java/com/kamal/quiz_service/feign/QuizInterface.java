package com.kamal.quiz_service.feign;


import com.kamal.quiz_service.model.QuestionWrapper;
import com.kamal.quiz_service.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("/question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String category, @RequestParam int numQ
    );

    @PostMapping("question/getQuestions")
    public  ResponseEntity<List<QuestionWrapper>> getQuestionsFromID(
            @RequestBody List<Integer> questionIds //[1,3,5]
    );

    @PostMapping("/question/getScore")
    public ResponseEntity<Integer> getScore(
            @RequestBody List<UserResponse> responses
    );
}
