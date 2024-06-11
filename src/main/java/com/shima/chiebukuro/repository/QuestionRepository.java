package com.shima.chiebukuro.repository;

import java.util.List;

import com.shima.chiebukuro.QuestionForm;
import com.shima.chiebukuro.model.Question;

public interface QuestionRepository {

    void insertQuestion(QuestionForm questionForm);

    List<Question> findAll();

    List<String> findTitle();

}
