package com.shima.chiebukuro.service;

import java.util.List;

import com.shima.chiebukuro.QuestionForm;
import com.shima.chiebukuro.model.Question;

public interface QuestionService {

    List<Question> findByQuestions();

    List<String> findQuestionTitles();

    void insertQuestion(QuestionForm questionForm);

}
