package com.example.demo;

import java.util.List;

public interface QuestionService {

    List<Question> findByQuestions();

    void insert(QuestionForm questionForm);

}
