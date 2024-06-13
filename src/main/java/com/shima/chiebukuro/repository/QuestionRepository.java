package com.shima.chiebukuro.repository;

import java.util.List;

import com.shima.chiebukuro.model.Question;
import com.shima.chiebukuro.model.QuestionForm;

public interface QuestionRepository {

    void insertQuestion(QuestionForm questionForm);

    Question findByQuestion(String id);

    List<Question> findAll();
}
