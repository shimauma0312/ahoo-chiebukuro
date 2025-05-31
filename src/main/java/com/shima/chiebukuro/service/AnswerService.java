package com.shima.chiebukuro.service;

import java.util.List;

import com.shima.chiebukuro.model.Answer;
import com.shima.chiebukuro.model.AnswerForm;

public interface AnswerService {

    List<Answer> findByAnswerContent(String id);

    void insertAnswer(AnswerForm answerForm);
    
    void incrementEmpathyCount(int answerId);

}
