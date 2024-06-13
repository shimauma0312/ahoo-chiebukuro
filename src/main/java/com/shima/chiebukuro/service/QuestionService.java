package com.shima.chiebukuro.service;

import java.util.List;

import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.model.QuestionForm;

public interface QuestionService {

    List<Question> findByQuestions();

    void insertQuestion(QuestionForm questionForm);

}
