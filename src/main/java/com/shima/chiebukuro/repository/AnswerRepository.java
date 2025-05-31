package com.shima.chiebukuro.repository;

import java.util.List;

import com.shima.chiebukuro.model.Answer;
import com.shima.chiebukuro.model.AnswerForm;

public interface AnswerRepository {
    void insertAnswer(AnswerForm answerForm);

    List<Answer> findByAnswerContent(String id);
    
    void incrementEmpathyCount(int answerId);
}
